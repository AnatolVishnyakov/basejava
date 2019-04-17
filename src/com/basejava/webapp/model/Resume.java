package com.basejava.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);

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

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, new Contact(contact));
    }

    public void setSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return String.format("UUID: %s FULL_NAME: %s", uuid, fullName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
    }

    @Override
    public int compareTo(Resume o) {
        return Comparator.comparing(Resume::getUuid)
                .thenComparing(Resume::getFullName)
                .compare(this, o);
    }
}
