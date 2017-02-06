package com.bank.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Entity for storing instruments names in db
 */
@Document
public class InstrumentName implements Serializable {
    private static final long serialVersionUID = -4426614403338260576L;

    @Id
    private String id;

    @Indexed
    private final String name;

    public InstrumentName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
