package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume resume) {
        index = (-1 * index) - 1;
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume with uuid=%s already exists.", resume.getUuid()));
        } else {
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        }
    }

    private int indexOf(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected int indexOf(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return indexOf(resume);
    }
}
