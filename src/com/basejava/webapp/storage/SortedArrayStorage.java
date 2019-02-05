package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int indexOf(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertElement(int index, Resume resume) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    protected void deleteElementByIndex(int index) {
        int indexLastElement = size - 1;
        if (index == indexLastElement) {
            storage[indexLastElement] = null;
        } else {
            System.arraycopy(storage, index + 1, storage, index, size);
        }
    }
}
