package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Instrument application starter for test.
 */
@SpringBootApplication
public class InstrumentApplicationTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Test application");
        SpringApplication.run(InstrumentApplicationTest.class, args);
    }
}