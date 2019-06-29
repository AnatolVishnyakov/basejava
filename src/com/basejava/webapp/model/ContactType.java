package com.basejava.webapp.model;

import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
    @SerializedName("Тел.")
    PHONE("Тел."),
    @SerializedName("Skype")
    SKYPE("Skype"),
    @SerializedName("Почта")
    EMAIL("Почта"),
    @SerializedName("Профиль LinkedIn")
    LINKEDIN("Профиль LinkedIn"),
    @SerializedName("Профиль GitHub")
    GITHUB("Профиль GitHub"),
    @SerializedName("Профиль StackOverflow")
    STACKOVERFLOW("Профиль StackOverflow"),
    @SerializedName("Домашняя страница")
    WEBSITE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
