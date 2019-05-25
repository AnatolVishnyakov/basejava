package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamStrategy implements AbstractStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeContent(dataOutputStream, resume.getContacts().entrySet(), contact -> {
                dataOutputStream.writeUTF(contact.getKey().name());
                dataOutputStream.writeUTF(contact.getValue());
            });

            writeContent(dataOutputStream, resume.getSections().entrySet(), entity -> {
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
                                writeContent(dataOutputStream, ((ListSection) section).getContents(), dataOutputStream::writeUTF);
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                writeContent(dataOutputStream, ((InstitutionSection) section).getInstitutions(), institution -> {
                                    dataOutputStream.writeUTF(institution.getHomePage().getName());
                                    dataOutputStream.writeUTF(institution.getHomePage().getUrl());
                                    writeContent(dataOutputStream, institution.getPositions(), position -> {
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

    private <T> void writeContent(DataOutputStream dataOutputStream, Collection<T> collection, DataStreamWriter<T> writer) throws IOException {
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

    @FunctionalInterface
    private interface DataStreamWriter<T> {
        void write(T t) throws IOException;

    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            // read Contact
            readContent(dataInputStream, () -> resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            // read Section
            readContent(dataInputStream, () -> readSection(dataInputStream, resume));
            return resume;
        }
    }

    private <T> void readContent(DataInputStream dataInputStream, SectionHandler<T> section) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            section.handle();
        }
    }

    private void readSection(DataInputStream dataInputStream, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                resume.setSection(sectionType, new TextSection(dataInputStream.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.setSection(sectionType, new ListSection(readElementList(dataInputStream, dataInputStream::readUTF)));
                break;
            case EXPERIENCE:
            case EDUCATION:
                resume.setSection(sectionType, new InstitutionSection(readElementList(dataInputStream, () ->
                        new Institution(
                                new HyperLink(dataInputStream.readUTF(), dataInputStream.readUTF()),
                                readElementList(dataInputStream, () -> new Institution.Position(
                                        dataInputStream.readUTF(), readLocalDate(dataInputStream), readLocalDate(dataInputStream), dataInputStream.readUTF())
                                )
                        )
                )));
                break;
            default:
                throw new IllegalStateException(String.format("Не корректная секция: %s", sectionType));
        }
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt());
    }

    private <T> List<T> readElementList(DataInputStream dataInputStream, DataStreamReader<T> reader) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    @FunctionalInterface
    private interface DataStreamReader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    private interface SectionHandler<T> {
        void handle() throws IOException;
    }
}
