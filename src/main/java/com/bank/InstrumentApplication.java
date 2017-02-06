package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Instrument application starter.
 */
@SpringBootApplication
public class InstrumentApplication {

    public static void main(String[] args) throws Exception {
        System.out.println("Main application.");
        SpringApplication.run(InstrumentApplication.class, args);

    }
}