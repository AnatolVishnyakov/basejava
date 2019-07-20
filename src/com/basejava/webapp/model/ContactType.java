package com.basejava.webapp.model;

import static java.lang.String.format;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return format("<a href='skype:%s'>%s</a>", value, value);
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return format("<a href='mailto:%s'>%s</a>", value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    WEBSITE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null)
                ? "" : toHtml0(value);
    }
}
