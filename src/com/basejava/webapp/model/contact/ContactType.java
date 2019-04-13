package com.basejava.webapp.model.contact;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    WEBSITE("Домашняя страница");

    private final String _title;

    ContactType(String title) {
        this._title = title;
    }

    @Override
    public String toString() {
        return _title;
    }
}
