package com.basejava.webapp.utils;

import java.time.LocalDate;
import java.time.Month;

public class DateUtils {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
