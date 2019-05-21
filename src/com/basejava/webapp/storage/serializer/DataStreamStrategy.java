package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements AbstractStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dataOutputStream.writeUTF(contact.getKey().name());
                dataOutputStream.writeUTF(contact.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                dataOutputStream.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeTextSection(dataOutputStream, (TextSection) section.getValue());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dataOutputStream, (ListSection) section.getValue());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeInstitutionSection(dataOutputStream, (InstitutionSection) section.getValue());
                        break;
                }
            }
        }
    }

    private void writeTextSection(DataOutputStream dataOutputStream, TextSection section) throws IOException {
        dataOutputStream.writeUTF(section.getContent());
    }

    private void writeListSection(DataOutputStream dataOutputStream, ListSection section) throws IOException {
        List<String> contents = section.getContents();
        dataOutputStream.writeInt(contents.size());
        for (String content : contents) {
            dataOutputStream.writeUTF(content);
        }
    }

    private void writeInstitutionSection(DataOutputStream dataOutputStream, InstitutionSection section) throws IOException {
        List<Institution> institutions = section.getInstitutions();
        dataOutputStream.writeInt(institutions.size());

        for (Institution institution : institutions) {
            dataOutputStream.writeUTF(institution.getHomePage().getName());
            dataOutputStream.writeUTF(institution.getHomePage().getUrl());
            writePositions(dataOutputStream, institution);
        }
    }

    private void writePositions(DataOutputStream dataOutputStream, Institution institution) throws IOException {
        List<Institution.Position> positions = institution.getPositions();
        dataOutputStream.writeInt(positions.size());

        for (Institution.Position position : positions) {
            dataOutputStream.writeUTF(position.getTitle());
            writeLocalDate(dataOutputStream, position.getStartDate());
            writeLocalDate(dataOutputStream, position.getEndDate());
            dataOutputStream.writeUTF(position.getDescription());
        }
    }

    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonthValue());
        dataOutputStream.writeInt(localDate.getDayOfMonth());
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int sizeContact = dataInputStream.readInt();
            for (int i = 0; i < sizeContact; i++) {
                resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            int sizeSection = dataInputStream.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(sectionType, new TextSection(dataInputStream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, new ListSection(readListSection(dataInputStream)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(sectionType, new InstitutionSection(readInstitutionSection(dataInputStream)));
                        break;
                }
            }
            return resume;
        }
    }

    private List<Institution> readInstitutionSection(DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<Institution> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Institution institution = new Institution(
                    new HyperLink(dataInputStream.readUTF(), dataInputStream.readUTF()),
                    readPosition(dataInputStream)
            );
            list.add(institution);
        }
        return list;
    }

    private List<Institution.Position> readPosition(DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<Institution.Position> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Institution.Position position = new Institution.Position(
                    dataInputStream.readUTF(),
                    readLocalDate(dataInputStream),
                    readLocalDate(dataInputStream),
                    dataInputStream.readUTF()
            );
            list.add(position);
        }
        return list;
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt());
    }

    private List<String> readListSection(DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dataInputStream.readUTF());
        }
        return list;
    }
}
