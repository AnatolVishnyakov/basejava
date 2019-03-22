package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage<T extends Integer> extends AbstractStorage<T> {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

    @Override
    protected void deleteElement(T index) {
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

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
    protected Resume getElement(T index) {
        return storage[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void insertElement(T index, Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        int insertIndex = prepareInsertPosition(index);
        storage[insertIndex] = resume;
        size++;
    }

    protected abstract int prepareInsertPosition(int index);

    protected abstract void removeResume(int index);

    @Override
    protected boolean isExist(Integer key) {
        return key > RESUME_NOT_FOUND;
    }

    @Override
    protected T searchKey(String uuid) {
        return indexOf(uuid);
    }

    protected abstract T indexOf(String uuid);
}
