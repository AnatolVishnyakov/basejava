package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serializer.AbstractStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private AbstractStrategy strategy;

    protected FileStorage(File directory, AbstractStrategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s is not directory", directory.getAbsolutePath()));
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(String.format("%s is not readable/writable", directory.getAbsolutePath()));
        }
        this.strategy = strategy;
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

    @Override
    protected Resume getElement(File file) {
        try {
            return strategy.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            strategy.writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
    protected List<Resume> convertToListStorage() {
        File[] files = directory.listFiles();
        Objects.requireNonNull(files, String.format("Files is not found to directory %s", directory.getAbsolutePath()));
        if (files.length > 0) {
            List<Resume> resumes = new ArrayList<>();
            for (File file : files) {
                try {
                    Resume resume = strategy.readResume(new FileInputStream(file));
                    resumes.add(resume);
                } catch (IOException e) {
                    throw new StorageException("Error read resume", null, e);
                }
            }
            return resumes;
        }
        return Collections.emptyList();

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
