package com.bank.calculation.delayed;

import com.bank.calculation.delayed.statistics.DelayedStatistic;
import com.bank.calculation.delayed.statistics.request.SortDirection;
import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.InstrumentName;
import com.bank.domain.StatisticEntity;
import com.bank.repository.InstrumentNameRepository;
import com.bank.repository.StatisticsRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DelayedStatisticsCalculationManageImpl implements DelayedStatisticsCalculationManage {

    /**
     * Page size for getting stored instruments names
     */
    private static final int NAME_PAGE_SIZE = 100;

    /**
     * Known instruments names
     */
    private static final String INSTRUMENT1 = "INSTRUMENT1";
    private static final String INSTRUMENT2 = "INSTRUMENT2";
    private static final String INSTRUMENT3 = "INSTRUMENT3";

    /**
     * How much instruments to sum for sum statistic
     */
    private static final int INSTRUMENTS_TO_SUM_LIMIT = 10;
    /**
     * Year and month for statistic calculation
     */
    private static final int YEAR_FOR_MEAN_CALCULATION = 2014;
    private static final Month MONTH_FOR_MEAN_CALCULATION = Month.NOVEMBER;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private InstrumentNameRepository instrumentNameRepository;

    @Autowired
    @Qualifier("mean")
    private DelayedStatistic delayedMeanStatistic;

    @Autowired
    @Qualifier("meanByDate")
    private DelayedStatistic delayedMeanByDateStatistic;

    @Autowired
    @Qualifier("sum")
    private DelayedStatistic delayedSumStatistic;

    /**
     * Contains of list of registered statistics calculations for instrument name
     */
    private Map<String, List<DelayedStatistic>> instrumentNameToCalculation;

    /**
     * What request used for instrument name
     */
    private Map<String, StatisticRequest> instrumentNameToRequest;

    /**
     * Instrument that shouldn't be processed hear
     */
    private List<String> instrumentsToExclude;

    @PostConstruct
    public void init() {
        //init exclude list
        instrumentsToExclude = new ArrayList<>();
        instrumentsToExclude.add(INSTRUMENT3);

        //init statistic requests for known instruments
        instrumentNameToRequest = new HashMap<>();
        StatisticRequest requestMean = new StatisticRequest.StatisticRequestBuilder()
                .instrumentName(INSTRUMENT1)
                .createRequest();

        StatisticRequest requestMeanByDate = new StatisticRequest.StatisticRequestBuilder()
                .instrumentName(INSTRUMENT2)
                .year(YEAR_FOR_MEAN_CALCULATION)
                .month(MONTH_FOR_MEAN_CALCULATION)
                .createRequest();

        instrumentNameToRequest.put(INSTRUMENT1, requestMean);
        instrumentNameToRequest.put(INSTRUMENT2, requestMeanByDate);

        //init statistic calculation mapping for known instruments
        instrumentNameToCalculation = new HashMap<>();
        instrumentNameToCalculation.put(INSTRUMENT1, Lists.newArrayList(delayedMeanStatistic));
        instrumentNameToCalculation.put(INSTRUMENT2, Lists.newArrayList(delayedMeanByDateStatistic));
    }


    @Override
    public void calculateAllRegisteredStatistics() {
        Page<InstrumentName> instrumentNames = instrumentNameRepository.findAll(new PageRequest(0, NAME_PAGE_SIZE));
        for (InstrumentName instrumentName : instrumentNames) {
            if (!instrumentsToExclude.contains(instrumentName.toString())) {
                if (instrumentNameToCalculation.containsKey(instrumentName.toString())) {
                    for (DelayedStatistic delayedStatistic : instrumentNameToCalculation.get(instrumentName.toString())) {
                        statisticsRepository.save(
                                StatisticEntity.of(delayedStatistic.calculateAndGet(
                                        instrumentNameToRequest.get(instrumentName.toString()))
                                ));
                    }
                } else {
                    StatisticRequest requestLimitSum = new StatisticRequest.StatisticRequestBuilder()
                            .instrumentName(instrumentName.toString())
                            .limit(INSTRUMENTS_TO_SUM_LIMIT)
                            .direction(SortDirection.DESC)
                            .createRequest();

                    statisticsRepository.save(
                            StatisticEntity.of(delayedSumStatistic.calculateAndGet(requestLimitSum)));
                }
            }
        }
    }
}
