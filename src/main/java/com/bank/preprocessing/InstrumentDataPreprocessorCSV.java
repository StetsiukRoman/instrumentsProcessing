package com.bank.preprocessing;

import com.bank.calculation.onthefly.OnlineStatisticCalculationManager;
import com.bank.domain.InstrumentEntity;
import com.bank.domain.InstrumentDataRecord;
import com.bank.domain.InstrumentName;
import com.bank.domain.InstrumentPriceModifierEntity;
import com.bank.repository.InstrumentNameRepository;
import com.bank.repository.InstrumentRepository;
import com.bank.repository.InstrumentPriceModifierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class InstrumentDataPreprocessorCSV implements InstrumentDataPreprocessor {
    /**
     * Logging util.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentDataPreprocessorCSV.class);
    /**
     * Current csv delimiter.
     */
    public static final String CSV_DELIMITER = ",";

    private static final String DATE_FORMAT_PATTERN = "dd-MMM-yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

    public static final String PROCESS_DATES_BEFORE = "19-Dec-2014";
    private static final Object LOCK = new Object();
    public LocalDate assumedCurrentDate = LocalDate.parse(PROCESS_DATES_BEFORE, formatter);

    @Value("${instruments.csvInstrumentsFile}")
    private String csvInstrumentsFile;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentPriceModifierRepository instrumentPriceModifierRepository;

    @Autowired
    private OnlineStatisticCalculationManager onlineStatisticCalculationManager;

    @Autowired
    private InstrumentNameRepository instrumentNameRepository;

    @Override
    public void processInstrumentData() {

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File instrumentsFile = new File(classLoader.getResource(csvInstrumentsFile).getFile());
        try (Scanner scanner = new Scanner(instrumentsFile)) {
            while (scanner.hasNext()) {
                InstrumentDataRecord record = parseLineAsInstrumentData(scanner.nextLine());
                if (isDateValid(record.getLocalDate())) {
                    //store distinct instrument names
                    if (instrumentNameRepository.findByName(record.getName()) == null)
                        instrumentNameRepository.save(new InstrumentName(record.getName()));

                    Double multiplier = getInstrumentMultiplier(record.getName());
                    //calculate online statistic if necessary
                    if (onlineStatisticCalculationManager.isCalculatedOnline(record.getName())) {
                        record.multiplyValueOn(multiplier);
                        onlineStatisticCalculationManager.calculateOnlineStatisticsFor(record);
                    }
                    instrumentRepository.save(InstrumentEntity.of(record.getName(), record.getLocalDate(), record.getValue() * multiplier));
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("processInstrumentData(): file with instruments data not found on path:{}", csvInstrumentsFile);
        } finally {
            // save online calculated statistics on datastore
            onlineStatisticCalculationManager.saveCalculatedStatistics();
        }
    }


    /**
     * Check if date is business date and before assumed current date
     */
    private boolean isDateValid(LocalDate date) {
        return !date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                && date.isBefore(assumedCurrentDate);
    }


    private Double getInstrumentMultiplier(String name) {
        InstrumentPriceModifierEntity modifierEntity = instrumentPriceModifierRepository.findByNameAllIgnoringCase(name);
        return modifierEntity != null? modifierEntity.getMultiplier() : 1;
    }

    public synchronized static InstrumentDataRecord parseLineAsInstrumentData(String csvLine) {
        String[] slittedLine = csvLine.split(CSV_DELIMITER);
        return InstrumentDataRecord.of(slittedLine[0], convertToDate(slittedLine[1]), convertToDouble(slittedLine[2]));

    }

    private static Double convertToDouble(String stringDouble) {
        checkNotNull(stringDouble);
        return Double.parseDouble(stringDouble);
    }

    private static LocalDate convertToDate(String stringDate) {
        checkNotNull(stringDate);
        return LocalDate.parse(stringDate, formatter);
    }

}