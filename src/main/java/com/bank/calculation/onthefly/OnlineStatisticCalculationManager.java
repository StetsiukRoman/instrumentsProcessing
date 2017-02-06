package com.bank.calculation.onthefly;

import com.bank.domain.InstrumentDataRecord;

/**
 * Managing "on the fly" statistic calculation
 */
public interface OnlineStatisticCalculationManager {

    /**
     * Check is some statistics should be calculated
     * online for give instrument
     * @param name instrument name
     */
    boolean isCalculatedOnline(String name);

    /**
     * Add record {@link InstrumentDataRecord} to calculation process
     * @param record
     */
    void calculateOnlineStatisticsFor(InstrumentDataRecord record);

    /**
     * save all on the fly calculated statistics
     */
    void saveCalculatedStatistics();
}
