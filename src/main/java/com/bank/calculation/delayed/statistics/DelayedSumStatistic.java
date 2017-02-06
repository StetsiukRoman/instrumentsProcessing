package com.bank.calculation.delayed.statistics;

import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.Statistic;
import com.bank.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Sum elements for request
 */
@Component
@Qualifier("sum")
public class DelayedSumStatistic implements DelayedStatistic {
    private static final String STATISTIC_NAME = "SUM";

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Statistic calculateAndGet(StatisticRequest statisticRequest) {
        Statistic sumStatisticForLast = instrumentRepository.getSumStatisticForLast(
                statisticRequest.getInstrumentName(),
                statisticRequest.getLimit(),
                statisticRequest.getDirection()
        );

        if (sumStatisticForLast == null) {
            sumStatisticForLast = Statistic.of(statisticRequest.getInstrumentName(),STATISTIC_NAME, 0.0);
        }
        else {
            sumStatisticForLast.setStatisticName(STATISTIC_NAME);
        }

        return sumStatisticForLast;
    }

    @Override
    public String getStatisticName() {
        return STATISTIC_NAME;
    }
}
