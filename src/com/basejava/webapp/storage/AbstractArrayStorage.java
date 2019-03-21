package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateElementByIndex(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void insertElementByIndex(int index, Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        int insertIndex = prepareInsertPosition(index);
        storage[insertIndex] = resume;
        size++;
    }

    protected abstract int prepareInsertPosition(int index);
}
