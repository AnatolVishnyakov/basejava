package com.basejava.webapp.model;

/**
 * Секции "Позиция" и "Личные качества"
 * */
public class TextSection extends Section {
    private String content;

    @Override
    public void add(String content) {
        this.content = content;
    }
}
