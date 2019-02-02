package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementByIndex(int index) {
        storage[index] = storage[--size];
        storage[size] = null;
    }

    @Override
    protected void insertElement(int index, Resume resume) {
        storage[size++] = resume;
    }
}
