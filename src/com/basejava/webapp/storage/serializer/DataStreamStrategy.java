package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FunctionalInterface
interface DataStreamWriter<T> {
    void write(T t) throws IOException;
}

public class DataStreamStrategy implements AbstractStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeDataContainer(dataOutputStream, resume.getContacts().entrySet(), contact -> {
                dataOutputStream.writeUTF(contact.getKey().name());
                dataOutputStream.writeUTF(contact.getValue());
            });

            writeDataContainer(dataOutputStream, resume.getSections().entrySet(), entity -> {
                        SectionType sectionType = entity.getKey();
                        AbstractSection section = entity.getValue();
                        dataOutputStream.writeUTF(sectionType.name());
                        switch (sectionType) {
                            case PERSONAL:
                            case OBJECTIVE:
                                dataOutputStream.writeUTF(((TextSection) section).getContent());
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                writeDataContainer(dataOutputStream, ((ListSection) section).getContents(), dataOutputStream::writeUTF);
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                writeDataContainer(dataOutputStream, ((InstitutionSection) section).getInstitutions(), institution -> {
                                    dataOutputStream.writeUTF(institution.getHomePage().getName());
                                    dataOutputStream.writeUTF(institution.getHomePage().getUrl());
                                    writeDataContainer(dataOutputStream, institution.getPositions(), position -> {
                                        dataOutputStream.writeUTF(position.getTitle());
                                        writeLocalDate(dataOutputStream, position.getStartDate());
                                        writeLocalDate(dataOutputStream, position.getEndDate());
                                        dataOutputStream.writeUTF(position.getDescription());
                                    });
                                });
                                break;
                        }
                    }
            );
        }
    }

    private <T> void writeDataContainer(DataOutputStream dataOutputStream, Collection<T> collection, DataStreamWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
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
