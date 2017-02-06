package com.bank.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents statistics in DB
 */
@Document
public class StatisticEntity implements Serializable{
    private static final long serialVersionUID = -5122760977304248181L;

    @Id
    public String id;

    private String statisticName;

    @Indexed
    private String instrumentName;
    private Double value;

    public StatisticEntity(String statisticName, String instrumentName, Double value) {
        this.statisticName = statisticName;
        this.instrumentName = instrumentName;
        this.value = value;
    }

    public static StatisticEntity of(String statisticName, String instrumentName, Double value) {
        checkNotNull(statisticName);
        checkNotNull(instrumentName);
        checkNotNull(value);
        checkArgument(value > 0);
        return new StatisticEntity(statisticName, instrumentName, value);
    }

    public static StatisticEntity of(Statistic statistic) {
        checkNotNull(statistic);
        checkNotNull(statistic.geInstrumentName());
        checkNotNull(statistic.getValue());
        checkArgument(statistic.getValue() > 0);

        return new StatisticEntity(statistic.getStatisticName(), statistic.geInstrumentName(), statistic.getValue());
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatisticEntity)) return false;

        StatisticEntity that = (StatisticEntity) o;

        if (!statisticName.equals(that.statisticName)) return false;
        if (!instrumentName.equals(that.instrumentName)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = statisticName.hashCode();
        result = 31 * result + instrumentName.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatisticEntity{");
        sb.append("statisticName='").append(statisticName).append('\'');
        sb.append(", instrumentName='").append(instrumentName).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
