package com.bank.calculation.onthefly.statistics;

import com.bank.domain.InstrumentDataRecord;
import com.bank.domain.Statistic;

/**
 * Statistic calculation while reading file with data
 */
public interface OnTheFlyCalculation {
    void initByInstrumentName(String instrumentName);

    void calculateFor(InstrumentDataRecord instrumentDataRecord);

    Statistic getResult();

    String getStatisticName();
}
