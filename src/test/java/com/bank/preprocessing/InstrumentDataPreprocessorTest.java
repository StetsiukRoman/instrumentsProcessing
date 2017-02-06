package com.bank.preprocessing;

import com.bank.InstrumentApplicationTest;
import com.bank.domain.StatisticEntity;
import com.bank.repository.InstrumentNameRepository;
import com.bank.repository.InstrumentRepository;
import com.bank.repository.StatisticsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InstrumentApplicationTest.class)
public class InstrumentDataPreprocessorTest {

    @Autowired
    private InstrumentDataPreprocessor instrumentDataPreprocessor;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Mock
    private InstrumentNameRepository instrumentNameRepository;

    @Before
    public void setUpBaseClass() {
        //before any test delete all database
        instrumentRepository.deleteAll();

    }

    @Test
    public void testProcessInstrumentData() throws Exception {
        instrumentDataPreprocessor.processInstrumentData();

        assertNull(statisticsRepository.findByInstrumentName("INSTRUMENT1"));
        assertNull(statisticsRepository.findByInstrumentName("INSTRUMENT2"));
        assertNull(statisticsRepository.findByInstrumentName("INSTRUMENT4"));

        //only one statistic for one instrument calculated on the fly while reading the file
        assertEquals(statisticsRepository.count(),1);
        assertEquals(statisticsRepository.findByInstrumentName("INSTRUMENT3"),
                StatisticEntity.of("VARIANCE", "INSTRUMENT3", 25.0));
    }
}