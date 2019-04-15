package com.basejava.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, Section> sections = new HashMap<>();
    private final Map<ContactType, Contact> contacts = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
        sections.put(SectionType.PERSONAL, new TextSection());
        sections.put(SectionType.OBJECTIVE, new TextSection());
        sections.put(SectionType.ACHIEVEMENT, new ListSection());
        sections.put(SectionType.QUALIFICATIONS, new ListSection());
        sections.put(SectionType.EXPERIENCE, new InstitutionSection());
        sections.put(SectionType.EDUCATION, new InstitutionSection());
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

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, new Contact(contact));
    }

    public void addPosition(String position) {
        Section positions = sections.get(SectionType.OBJECTIVE);
        positions.add(position);
    }

    public void addPersonal(String personal) {
        Section personals = sections.get(SectionType.PERSONAL);
        personals.add(personal);
    }

    public void addAchievement(String achievement) {
        Section achievements = sections.get(SectionType.ACHIEVEMENT);
        achievements.add(achievement);
    }

    public void addQualification(String qualification) {
        Section qualifications = sections.get(SectionType.QUALIFICATIONS);
        qualifications.add(qualification);
    }

    public void addExperience(String title, String duration, String experience) {
        Section experiences = sections.get(SectionType.EXPERIENCE);
        experiences.add(String.format("%s | %s | %s", title, duration, experience));
    }

    public void addEducation(String title, String duration, String courseName) {
        Section education = sections.get(SectionType.EDUCATION);
        education.add(String.format("%s | %s | %s", title, duration, courseName));
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }
}
