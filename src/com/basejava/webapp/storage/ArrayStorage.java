package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    private static final int RESUME_NOT_FOUND = -1;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            System.out.println("Array index out of bounds.");
            return;
        }
        int index = indexOf(resume.getUuid());
        if (index != RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume with uuid=%s already exists.", resume.getUuid()));
        } else {
            storage[size++] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", uuid));
            return null;
        }

        return storage[index];
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", uuid));
        } else {
            storage[index] = storage[--size];
            storage[size] = null;
        }
    }

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", resume.getUuid()));
        } else {
            storage[index] = resume;
        }
    }

    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }
}
