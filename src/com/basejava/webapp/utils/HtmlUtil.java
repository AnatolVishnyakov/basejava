package com.basejava.webapp.utils;

import com.basejava.webapp.model.Institution;

public class HtmlUtil {
    public static String formatDates(Institution.Position position) {
        return String.format("%s - %s", DateUtils.format(position.getStartDate()), DateUtils.format(position.getEndDate()));
    }
}
