package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public ArrayStorage() {
        super(DEFAULT_CAPACITY);
    }

    public ArrayStorage(int capacity) {
        super(capacity);
    }

    @Override
    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }

    @Override
    protected void insertElement(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElementByIndex(int index) {
        storage[index] = storage[size - 1];
    }
}
