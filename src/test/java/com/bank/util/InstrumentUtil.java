package com.bank.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkNotNull;

public class InstrumentUtil {
    public static final String DATE_FORMAT_PATTERN = "dd-MMM-yyyy";

    public static LocalDate convertToDate(String stringDate) {
        checkNotNull(stringDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return LocalDate.parse(stringDate, formatter);
    }
}
