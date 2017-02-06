package com.bank.calculation.onthefly.statistics;

import com.bank.domain.InstrumentDataRecord;
import com.bank.domain.Statistic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Variance algorithm
 */
@Component
@Qualifier("variance")
public class OnTheFlyCalculationVariance implements OnTheFlyCalculation {
    private static final String STATISTIC_NAME = "VARIANCE";

    private String instrumentName;

    private long n = 0;
    private double mean = 0.0;
    private double M2 = 0.0;

    @Override
    public void initByInstrumentName(String instrumentName) {
        checkNotNull(instrumentName);
        this.instrumentName = instrumentName;
    }

    @Override
    public void calculateFor(InstrumentDataRecord instrumentDataRecord) {
        double v = instrumentDataRecord.getValue();

        n += 1;
        double delta = v - mean;
        mean += delta/n;
        double delta2 = v - mean;
        M2 += delta*delta2;
    }

    @Override
    public Statistic getResult() {
        return Statistic.of(getStatisticName(), instrumentName, M2 / (n));
    }

    @Override
    public String getStatisticName() {
        return STATISTIC_NAME;
    }
}
