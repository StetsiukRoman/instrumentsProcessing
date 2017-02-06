package com.bank.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represent statistic value object
 */
public class Statistic {
    private String statisticName;
    private String instrumentName;
    private Double value;

    private Statistic(String statisticName, String instrumentName, Double value) {
        this.statisticName = statisticName;
        this.instrumentName = instrumentName;
        this.value = value;
    }

    public static Statistic of(String statisticName, String instrumentName, Double value) {
        checkNotNull(statisticName);
        checkNotNull(instrumentName);
        checkNotNull(value);
        checkArgument(value > 0);
        return new Statistic(statisticName, instrumentName, value);
    }

    public String getStatisticName() {
        return statisticName;
    }

    public String geInstrumentName() {
        return instrumentName;
    }

    public Double getValue() {
        return value;
    }

    public void setStatisticName(String statisticName) {
        this.statisticName = statisticName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;

        Statistic statistic = (Statistic) o;

        if (statisticName != null ? !statisticName.equals(statistic.statisticName) : statistic.statisticName != null)
            return false;
        if (instrumentName != null ? !instrumentName.equals(statistic.instrumentName) : statistic.instrumentName != null)
            return false;
        return value != null ? value.equals(statistic.value) : statistic.value == null;

    }

    @Override
    public int hashCode() {
        int result = statisticName != null ? statisticName.hashCode() : 0;
        result = 31 * result + (instrumentName != null ? instrumentName.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Statistic{");
        sb.append("statisticName='").append(statisticName).append('\'');
        sb.append(", instrumentName='").append(instrumentName).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
