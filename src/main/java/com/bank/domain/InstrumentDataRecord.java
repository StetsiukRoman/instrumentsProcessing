package com.bank.domain;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents record in datasource
 */
public class InstrumentDataRecord {
    private String name;
    private LocalDate date;
    private Double value;

    private InstrumentDataRecord(String name, LocalDate date, Double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public static InstrumentDataRecord of(String name, LocalDate date, Double value) {
        checkNotNull(name);
        checkNotNull(date);
        checkNotNull(value);
        checkArgument(value > 0);
        return new InstrumentDataRecord(name, date, value);
    }

    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public void multiplyValueOn(Double multiplier) {
        checkNotNull(multiplier);
        checkArgument(multiplier > 0);
        this.value *=multiplier;
    }
}
