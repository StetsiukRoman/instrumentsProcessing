package com.bank.repository;

import com.bank.domain.InstrumentName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstrumentNameRepository extends MongoRepository<InstrumentName, String> {
    InstrumentName findByName(String name);
}