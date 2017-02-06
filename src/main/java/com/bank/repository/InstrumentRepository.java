package com.bank.repository;

import com.bank.domain.InstrumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstrumentRepository extends MongoRepository<InstrumentEntity, String>, InstrumentRepositoryCustom {
}