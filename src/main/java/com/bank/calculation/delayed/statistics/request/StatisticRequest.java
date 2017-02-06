package com.bank.calculation.delayed.statistics.request;

import java.time.Month;

/**
 * Used for getting statistics
 */
public class StatisticRequest {
    private String instrumentName;
    private Month month;
    private int year;
    private int limit;
    private SortDirection direction;

    private StatisticRequest(String instrumentName, Month month, int year, int limit, SortDirection direction) {
        this.instrumentName = instrumentName;
        this.month = month;
        this.year = year;
        this.limit = limit;
        this.direction = direction;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getLimit() {
        return limit;
    }

    public SortDirection getDirection() {
        return direction;
    }

    /**
     * Builder pattern
     */
    public static class StatisticRequestBuilder {
        private String instrumentName;
        private Month month;
        private int year;
        private int limit;
        private SortDirection direction;

        public StatisticRequestBuilder(){
        }

        public StatisticRequestBuilder instrumentName(String instrumentName) {
            this.instrumentName = instrumentName;
            return this;
        }

        public StatisticRequestBuilder month(Month month) {
            this.month = month;
            return this;
        }
        public StatisticRequestBuilder year(int year) {
            this.year = year;
            return this;
        }
        public StatisticRequestBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }
        public StatisticRequestBuilder direction(SortDirection direction) {
            this.direction = direction;
            return this;
        }

        public StatisticRequest createRequest() {
            return new StatisticRequest(instrumentName, month, year, limit, direction);
        }

    }

}
