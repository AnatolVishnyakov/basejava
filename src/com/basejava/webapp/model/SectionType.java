package com.basejava.webapp.model;

import java.util.Arrays;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public static boolean isTextSection(SectionType sectionType) {
        return Arrays.asList(SectionType.PERSONAL, SectionType.OBJECTIVE).contains(sectionType);
    }

    public static boolean isListSection(SectionType sectionType) {
        return Arrays.asList(SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS).contains(sectionType);
    }

    public static boolean isInstitutionSection(SectionType sectionType) {
        return Arrays.asList(SectionType.EXPERIENCE, SectionType.EDUCATION).contains(sectionType);
    }

    @Override
    public String toString() {
        return title;
    }
}
