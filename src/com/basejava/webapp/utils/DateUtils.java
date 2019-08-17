package com.basejava.webapp.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final String NOW_LABEL = "Сейчас";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW)
                ? NOW_LABEL
                : date.format(DATE_FORMAT);
    }

    public static LocalDate parse(String date) {
        if (HtmlUtil.isEmpty(date) || NOW_LABEL.equals(date)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMAT);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}