package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected static final Integer RESUME_NOT_FOUND = -1;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void deleteElement(Integer index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        int insertIndex = prepareInsertPosition(index);
        storage[insertIndex] = resume;
        size++;
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage[index];
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index > RESUME_NOT_FOUND;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int prepareInsertPosition(int index);

    protected abstract void removeResume(int index);
}
