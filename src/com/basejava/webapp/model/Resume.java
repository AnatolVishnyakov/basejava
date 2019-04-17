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

    public void setSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }
}
