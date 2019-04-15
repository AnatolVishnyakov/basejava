package com.basejava.webapp.model;

public class InstitutionSection extends Section {
    private String title;
    private String duration;
    private String experience;

    @Override
    public void add(String content) {
        String[] params = content.split(" | ");
        this.title = params[0];
        this.duration = params[1];
        this.experience = params[2];
    }
}
