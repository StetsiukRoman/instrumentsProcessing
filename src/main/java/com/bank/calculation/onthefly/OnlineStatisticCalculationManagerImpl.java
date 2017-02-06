package com.bank.calculation.onthefly;

import com.bank.calculation.onthefly.statistics.OnTheFlyCalculation;
import com.bank.domain.InstrumentDataRecord;
import com.bank.domain.StatisticEntity;
import com.bank.repository.StatisticsRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class OnlineStatisticCalculationManagerImpl implements OnlineStatisticCalculationManager{

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    @Qualifier("variance")
    private OnTheFlyCalculation onTheFlyCalculationVariance;

    //registered on the fly calculation for given instrument name
    private Map<String, List<OnTheFlyCalculation>> instrumentNameToCalculation;

    @PostConstruct
    public void init() {
        instrumentNameToCalculation = new HashMap<>();
        onTheFlyCalculationVariance.initByInstrumentName("INSTRUMENT3");
        instrumentNameToCalculation.put("INSTRUMENT3", Lists.newArrayList(onTheFlyCalculationVariance));
    }

    public boolean isCalculatedOnline(String name) {
        checkNotNull(name);
        return instrumentNameToCalculation.containsKey(name);
    }

    public void calculateOnlineStatisticsFor(InstrumentDataRecord record) {
        checkNotNull(record);
        instrumentNameToCalculation.get(record.getName()).forEach(algorithm -> algorithm.calculateFor(record));
    }

    public void saveCalculatedStatistics() {
        for (String name : instrumentNameToCalculation.keySet()) {
            for (OnTheFlyCalculation algorithm : instrumentNameToCalculation.get(name)) {
                statisticsRepository.save(StatisticEntity.of(algorithm.getResult()));
            }
        }
    }
}
