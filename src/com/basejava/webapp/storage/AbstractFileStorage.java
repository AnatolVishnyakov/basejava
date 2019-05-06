package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s is not directory", directory.getAbsolutePath()));
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(String.format("%s is not readable/writable", directory.getAbsolutePath()));
        }
        this.directory = directory;
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected void insertElement(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateElement(file, resume);
    }

    protected abstract void writeResumeToFile(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume readResumeFromFile(InputStream inputStream) throws IOException;

    @Override
    protected Resume getElement(File file) {
        try {
            return readResumeFromFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            writeResumeToFile(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, String.format("Files is not exist to directory %s", directory.getAbsolutePath()));
        for (File file : files) {
            deleteElement(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, "Directory read error");
        return files.length;
    }
}
