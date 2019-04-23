package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

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
        file.deleteOnExit();
    }

    @Override
    protected void insertElement(File file, Resume resume) {
        try {
            file.createNewFile();
            writeResumeToFile(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void writeResumeToFile(File file, Resume resume) throws IOException;

    protected abstract Resume readResumeFromFile(File file);

    @Override
    protected Resume getElement(File file) {
        return readResumeFromFile(file);
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            writeResumeToFile(file, resume);
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
        for (File file : directory.listFiles()) {
            file.deleteOnExit();
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}
