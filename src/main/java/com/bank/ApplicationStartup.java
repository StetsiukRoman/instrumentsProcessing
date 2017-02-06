package com.bank;

import com.bank.calculation.delayed.DelayedStatisticsCalculationManage;
import com.bank.preprocessing.InstrumentDataPreprocessor;
import com.bank.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private InstrumentDataPreprocessor reader;

    @Autowired
    private DelayedStatisticsCalculationManage delayed;

    @Value("${instruments.calculate.real}")
    private boolean isExecuted;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to process data.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if(isExecuted) {
            System.out.println("Start processing.");
            long startTime = System.currentTimeMillis();
            reader.processInstrumentData();
            delayed.calculateAllRegisteredStatistics();
            long endTime = System.currentTimeMillis();
            System.out.println("Finish processing.");
            System.out.println("Total execution time: " + (endTime - startTime) + "ms");
            System.out.println("Result statistics:");
            System.out.println(statisticsRepository.findAll());
        }

        return;
    }

}