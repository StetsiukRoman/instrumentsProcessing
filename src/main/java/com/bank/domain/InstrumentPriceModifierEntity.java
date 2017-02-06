package com.bank.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Used to modify instrument price
 */
@Table(name = "INSTRUMENT_PRICE_MODIFIER")
@Entity
public class InstrumentPriceModifierEntity implements Serializable {

    private static final long serialVersionUID = -6604163299042227261L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Instrument name as read from the input source
     */
    @Column(nullable = false)
    private String name;

    /**
     * Specifies the factor by which the original instrument price should be multiplied.
     */
    @Column(nullable = false)
    private Double multiplier;


    protected InstrumentPriceModifierEntity() {
    }

    public InstrumentPriceModifierEntity(String name, Double multiplier) {
        super();
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() {
        return this.name;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return getName() + "," + getMultiplier();
    }

}