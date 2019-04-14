package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class QualificationSection extends Section {
    private List<String> content = new ArrayList<>();

    @Override
    public void add(String content) {
        this.content.add(content);
    }
}
