package com.bank.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public class SpringMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient("localhost"), "instruments");
    }

    @Bean
    public MongoClientOptions mongoClientOptions() {
        return (new MongoClientOptions.Builder())
                .maxConnectionIdleTime(100)
                .connectionsPerHost(10)
                .cursorFinalizerEnabled(false)
                .build();
    }



}
