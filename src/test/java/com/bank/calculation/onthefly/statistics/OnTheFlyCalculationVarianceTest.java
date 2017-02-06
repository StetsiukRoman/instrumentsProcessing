package com.bank.calculation.onthefly.statistics;

import com.bank.domain.InstrumentDataRecord;
import com.bank.domain.Statistic;
import com.bank.util.InstrumentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class OnTheFlyCalculationVarianceTest {

    private OnTheFlyCalculation onTheFlyCalculation;

    private List<InstrumentDataRecord> instrumentData;

    @Before
    public void setUp() {
        onTheFlyCalculation = new OnTheFlyCalculationVariance();
        instrumentData = new ArrayList<>();
        instrumentData.add(InstrumentDataRecord.of("NAME1", InstrumentUtil.convertToDate("12-Jun-2000"), 10.0));
        instrumentData.add(InstrumentDataRecord.of("NAME1", InstrumentUtil.convertToDate("13-Jun-2001"), 11.0));
        instrumentData.add(InstrumentDataRecord.of("NAME1", InstrumentUtil.convertToDate("14-Jun-2002"), 12.0));
        instrumentData.add(InstrumentDataRecord.of("NAME1", InstrumentUtil.convertToDate("15-Jun-2003"), 13.0));
        instrumentData.add(InstrumentDataRecord.of("NAME1", InstrumentUtil.convertToDate("16-Jun-2004"), 14.0));
        onTheFlyCalculation.initByInstrumentName("NAME1");
    }

    @Test
    public void testVarianceCalculation() {
        for (InstrumentDataRecord record : instrumentData) {
            onTheFlyCalculation.calculateFor(record);
        }
        Statistic result = onTheFlyCalculation.getResult();

        assertEquals(result, Statistic.of("VARIANCE","NAME1", 2.0));
    }
}