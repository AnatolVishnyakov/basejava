package com.basejava.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества", new General()),
    OBJECTIVE("Позиция", new General()),
    ACHIEVEMENT("Достижения", new Background()),
    QUALIFICATIONS("Квалификация", new Background()),
    EXPERIENCE("Опыт работы", new Experience()),
    EDUCATION("Образование", new Experience());

    private String title;
    private Information information;

    SectionType(String title, Information information) {
        this.title = title;
        this.information = information;
    }

    public String getTitle() {
        return title;
    }

    public Information getInformation() {
        return information;
    }
}
