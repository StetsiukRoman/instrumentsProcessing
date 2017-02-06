package com.bank.calculation.delayed.statistics;

import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.Statistic;

/**
 *  Used for calculation statistics after storing data into aggregation engine
 */
public interface DelayedStatistic {
    /**
     * Calculate and return statistic by request
     * @param statisticRequest
     * @return {@link Statistic} value object for statistic
     */
    Statistic calculateAndGet(StatisticRequest statisticRequest);

    /**
     * @return calculated statistic name
     */
    String getStatisticName();
}
