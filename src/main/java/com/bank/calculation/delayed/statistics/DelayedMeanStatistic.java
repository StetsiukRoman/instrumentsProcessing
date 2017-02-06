package com.bank.calculation.delayed.statistics;

import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.Statistic;
import com.bank.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Calculating average statistic for specified
 * in request {@link StatisticRequest} instrument name
 */
@Component
@Qualifier("mean")
public class DelayedMeanStatistic implements DelayedStatistic {
    private static final String STATISTIC_NAME = "MEAN";

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Statistic calculateAndGet(StatisticRequest statisticRequest) {
        Statistic meanStatisticForName = instrumentRepository.getMeanStatisticForName(statisticRequest.getInstrumentName());
        if (meanStatisticForName == null) {
            meanStatisticForName = Statistic.of(statisticRequest.getInstrumentName(),STATISTIC_NAME, 0.0);
        }
        else {
            meanStatisticForName.setStatisticName(STATISTIC_NAME);
        }

        return meanStatisticForName;
    }

    @Override
    public String getStatisticName() {
        return STATISTIC_NAME;
    }
}
