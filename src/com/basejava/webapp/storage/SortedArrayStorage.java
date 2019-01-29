package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            System.out.println("Array index out of bounds.");
            return;
        }
        int index = indexOf(resume.getUuid());
        if (index == 0) {
            System.out.println(String.format("Resume with uuid=%s already exists.", resume.getUuid()));
        } else {
            storage[size++] = resume;
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        try {
            return Arrays.binarySearch(storage, searchKey, Comparator.comparing(Resume::getUuid));
        } catch (NullPointerException exc) {
            return -1;
        }
    }
}
