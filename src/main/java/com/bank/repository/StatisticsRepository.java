package com.bank.repository;

import com.bank.domain.StatisticEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<StatisticEntity, String> {
    StatisticEntity findByInstrumentName(String instrumentName);
}