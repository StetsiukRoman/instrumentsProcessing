package com.bank.repository;

import com.bank.calculation.delayed.statistics.request.SortDirection;
import com.bank.domain.Statistic;

import java.time.Month;

/**
 * Aggregated statistics calculation on storage engine level
 */
public interface InstrumentRepositoryCustom {

    /**
     * Calculate mean value statistic for request data
     */
    Statistic getMeanStatisticForName(String instrumentName);

    /**
     * Calculate mean statistic for specified year and month
     */
    Statistic getMeanStatisticForDate(String instrumentName, int year, Month month);

    /**
     * Calculate sum of 'limit' latest or newest elements
     */
    Statistic getSumStatisticForLast(String instrumentName, int limit, SortDirection direction);
}