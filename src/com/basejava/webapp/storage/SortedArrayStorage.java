package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, --size);
    }

    @Override
    protected void insertElement(int index, Resume resume) {
        index = (-1 * index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }
}
