package com.basejava.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final SectionType personal = SectionType.PERSONAL;
    private final SectionType objective = SectionType.OBJECTIVE;
    private final SectionType achievement = SectionType.ACHIEVEMENT;
    private final SectionType qualifications = SectionType.QUALIFICATIONS;
    private final SectionType experience = SectionType.EXPERIENCE;
    private final SectionType education = SectionType.EDUCATION;
    private final List<Contact> contacts = new ArrayList<Contact>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return String.format("UUID: %s FULL_NAME: %s", uuid, fullName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resume resume = (Resume) o;
        return uuid.equals(resume.getUuid()) &&
                fullName.equals(resume.getFullName());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int compareTo(Resume o) {
        return Comparator.comparing(Resume::getUuid)
                .thenComparing(Resume::getFullName)
                .compare(this, o);
    }
}
