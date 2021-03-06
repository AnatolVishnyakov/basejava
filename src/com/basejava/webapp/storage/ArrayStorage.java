package com.basejava.webapp.storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        for (Integer i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }

    @Override
    protected int prepareInsertPosition(int index) {
        return size; // last position
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }
}
