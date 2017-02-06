package com.bank.repository;

import com.bank.domain.InstrumentPriceModifierEntity;
import org.springframework.data.repository.Repository;

public interface InstrumentPriceModifierRepository extends Repository<InstrumentPriceModifierEntity, Long> {
    InstrumentPriceModifierEntity findByNameAllIgnoringCase(String name);
}
