package com.bank.repository;

import com.bank.calculation.delayed.statistics.request.SortDirection;
import com.bank.domain.InstrumentEntity;
import com.bank.domain.Statistic;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Component;

import java.time.Month;

@Component
public class InstrumentRepositoryImpl implements InstrumentRepositoryCustom {

    @Autowired
    private MongoOperations operations;

    @Override
    public Statistic getMeanStatisticForName(String instrumentName) {
        AggregationResults<Statistic> results = operations.aggregate(newAggregation(InstrumentEntity.class,
                match(where("name").is(instrumentName)),
                project("value", "name"),
                group("name")
                        .avg("value").as("value")
                        .first("name").as("instrumentName"),
                project("instrumentName", "value")

        ), Statistic.class);

        return results.getUniqueMappedResult();
    }

    @Override
    public Statistic getMeanStatisticForDate(String instrumentName,  int year, Month month) {
        AggregationResults<Statistic> results = operations.aggregate(newAggregation(InstrumentEntity.class,
                match(where("name").is(instrumentName)),
                project("date","value", "name")
                        .andExpression("month(date)").as("month")
                        .andExpression("year(date)").as("year"),
                match(where("year").is(year).and("month").is(month.getValue())),
                group("name")
                        .avg("value").as("value")
                        .first("name").as("instrumentName"),

                project("instrumentName", "value")

        ), Statistic.class);

        return results.getUniqueMappedResult();
    }

    @Override
    public Statistic getSumStatisticForLast(String instrumentName, int limit, SortDirection direction) {
        AggregationResults<Statistic> results = operations.aggregate(newAggregation(InstrumentEntity.class,
                match(where("name").is(instrumentName)),
                project("date","value", "name"),
                sort(direction == SortDirection.DESC ? Sort.Direction.DESC: Sort.Direction.ASC, "date"),
                limit(limit),
                group("name")
                        .sum("value").as("value")
                        .first("name").as("instrumentName"),
                project("instrumentName", "value")

        ), Statistic.class);

        return results.getUniqueMappedResult();
    }


}
