package com.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Instrument entity representation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class InstrumentEntity implements Serializable {
    private static final long serialVersionUID = 373396132774141385L;

    @Id
    public String id;

    @Indexed
    private String name;
    private LocalDate date;
    private Double value;


    private InstrumentEntity(String name, LocalDate date, Double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public static InstrumentEntity of(String name, LocalDate date, Double value) {
        checkNotNull(name);
        checkNotNull(date);
        checkNotNull(value);
        checkArgument(value > 0);
        return new InstrumentEntity(name, date, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InstrumentEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

}

