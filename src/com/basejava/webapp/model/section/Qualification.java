package com.basejava.webapp.model.section;

import java.util.ArrayList;
import java.util.List;

public class Qualification extends Section {
    private List<String> content = new ArrayList<>();

    public List<String> getContent() {
        return content;
    }

    @Override
    public void add(String value) {
        content.add(value);
    }

    @Override
    public void add(String title, String duration, String value) {

    }
}
