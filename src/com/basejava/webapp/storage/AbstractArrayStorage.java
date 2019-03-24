package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage<T extends Integer> extends AbstractStorage<T> {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void deleteElement(T key) {
        removeResume(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(T key, Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        int insertIndex = prepareInsertPosition(key);
        storage[insertIndex] = resume;
        size++;
    }

    @Override
    protected Resume getElement(T key) {
        return storage[key];
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected boolean isExist(T key) {
        return key.intValue() > RESUME_NOT_FOUND;
    }

    @Override
    protected T getSearchKey(String uuid) {
        return indexOf(uuid);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract T indexOf(String uuid);

    protected abstract int prepareInsertPosition(int index);

    protected abstract void removeResume(int index);
}
