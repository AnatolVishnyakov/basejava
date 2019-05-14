package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(Path pathDirectory) {
        super(pathDirectory.toFile().getAbsolutePath());
    }

    @Override
    protected void writeResumeToFile(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    protected Resume readResumeFromFile(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    protected List<Resume> convertToListStorage() {
        File[] files = directory.toFile().listFiles();
        Objects.requireNonNull(files, String.format("Files is not found to directory %s", directory.toFile().getAbsolutePath()));
        if(files.length > 0) {
            List<Resume> resumes = new ArrayList<>();
            for (File file: files) {
                try {
                    Resume resume = readResumeFromFile(new FileInputStream(file));
                    resumes.add(resume);
                } catch (IOException e) {
                    throw new StorageException("Error read resume", null, e);
                }
            }
            return resumes;
        }
        return Collections.emptyList();

    }
}
