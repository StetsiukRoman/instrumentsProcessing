package com.bank.calculation.delayed.statistics;

import com.bank.InstrumentApplicationTest;
import com.bank.calculation.delayed.statistics.request.SortDirection;
import com.bank.calculation.delayed.statistics.request.StatisticRequest;
import com.bank.domain.InstrumentEntity;
import com.bank.domain.InstrumentName;
import com.bank.domain.Statistic;
import com.bank.repository.InstrumentNameRepository;
import com.bank.repository.InstrumentRepository;
import com.bank.util.InstrumentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;

import static org.junit.Assert.*;

@SpringBootTest(classes = InstrumentApplicationTest.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class DelayedStatisticsTest {
    @Autowired
    @Qualifier("meanByDate")
    private DelayedStatistic delayedMeanByDateStatistic;

    @Autowired
    @Qualifier("mean")
    private DelayedStatistic delayedMeanStatistic;

    @Autowired
    @Qualifier("sum")
    private DelayedStatistic delayedSumStatistic;


    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentNameRepository instrumentNameRepository;


    @Before
    public void setUp() throws Exception {
        //cleanup
        instrumentNameRepository.deleteAll();
        instrumentRepository.deleteAll();

        //fill with test data
        instrumentNameRepository.save(new InstrumentName("NAME1"));
        instrumentNameRepository.save(new InstrumentName("NAME2"));
        instrumentNameRepository.save(new InstrumentName("NAME4"));

        instrumentRepository.save(InstrumentEntity.of("NAME1", InstrumentUtil.convertToDate("21-Jul-2008"), 10.0));
        instrumentRepository.save(InstrumentEntity.of("NAME1", InstrumentUtil.convertToDate("22-Oct-2002"), 21.0));
        instrumentRepository.save(InstrumentEntity.of("NAME1", InstrumentUtil.convertToDate("23-Jul-2008"), 32.0));

        instrumentRepository.save(InstrumentEntity.of("NAME2", InstrumentUtil.convertToDate("24-Jan-2004"), 20.0));
        instrumentRepository.save(InstrumentEntity.of("NAME2", InstrumentUtil.convertToDate("25-Nov-2000"), 22.0));
        instrumentRepository.save(InstrumentEntity.of("NAME2", InstrumentUtil.convertToDate("27-Jan-2004"), 24.0));

        instrumentRepository.save(InstrumentEntity.of("NAME4", InstrumentUtil.convertToDate("28-Jan-2001"), 2.0));
        instrumentRepository.save(InstrumentEntity.of("NAME4", InstrumentUtil.convertToDate("29-Jan-2004"), 10.0));
        instrumentRepository.save(InstrumentEntity.of("NAME4", InstrumentUtil.convertToDate("22-Jan-1999"), 20.0));

    }


    @Test
    public void testMeanByInstrumentNameAndDateCalculation() throws Exception {
        StatisticRequest requestMeanByDate = new StatisticRequest.StatisticRequestBuilder()
                .instrumentName("NAME1")
                .year(2008)
                .month(Month.JULY)
                .createRequest();

        Statistic statistic = delayedMeanByDateStatistic.calculateAndGet(requestMeanByDate);
        assertEquals(statistic, Statistic.of("MEAN_BY_DATE","NAME1", 21.0));
    }

    @Test
    public void testMeanByInstrumentNameCalculation() throws Exception {
        StatisticRequest requestMean = new StatisticRequest.StatisticRequestBuilder()
                .instrumentName("NAME2")
                .createRequest();

        Statistic statistic = delayedMeanStatistic.calculateAndGet(requestMean);

        assertEquals(statistic, Statistic.of("MEAN","NAME2", 22.0));
    }

    @Test
    public void testSumLimitByInstrumentNameCalculation() throws Exception {
        StatisticRequest requestMean = new StatisticRequest.StatisticRequestBuilder()
                .instrumentName("NAME4")
                .limit(2)
                .direction(SortDirection.DESC)
                .createRequest();

        Statistic statistic = delayedSumStatistic.calculateAndGet(requestMean);
        assertEquals(statistic, Statistic.of("SUM","NAME4", 12.0));

    }


}