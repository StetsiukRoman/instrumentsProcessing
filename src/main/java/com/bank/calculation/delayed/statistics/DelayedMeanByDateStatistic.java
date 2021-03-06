package com.bank.calculation.delayed.statistics;

import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.Statistic;
import com.bank.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Calculate average value for specified year and month
 */
@Component
@Qualifier("meanByDate")
public class DelayedMeanByDateStatistic implements DelayedStatistic {
    private static final String STATISTIC_NAME = "MEAN_BY_DATE";

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Statistic calculateAndGet(StatisticRequest statisticRequest) {
        Statistic meanStatisticForDate = instrumentRepository.getMeanStatisticForDate(
                statisticRequest.getInstrumentName(),
                statisticRequest.getYear(),
                statisticRequest.getMonth()
        );

        if (meanStatisticForDate == null) {
            meanStatisticForDate = Statistic.of(statisticRequest.getInstrumentName(),STATISTIC_NAME, 0.0);
        }
        else {
            meanStatisticForDate.setStatisticName(STATISTIC_NAME);
        }
        return meanStatisticForDate;
    }

    @Override
    public String getStatisticName() {
        return STATISTIC_NAME;
    }
}
