package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    protected Path directory;

    protected AbstractPathStorage(String pathDirectory) {
        directory = Paths.get(pathDirectory);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.getFileName().toString());
            }
        } catch (IOException e) {
            throw new StorageException("File not exist", path.getFileName().toString());
        }
    }

    @Override
    protected void insertElement(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath().toString(), path.getFileName().toString(), e);
        }
        updateElement(path, resume);
    }

    protected abstract void writeResumeToFile(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume readResumeFromFile(InputStream inputStream) throws IOException;

    @Override
    protected Resume getElement(Path path) {
        try {
            return readResumeFromFile(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateElement(Path path, Resume resume) {
        try {
            writeResumeToFile(resume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Error when opening directory", null, e);
        }
    }
}