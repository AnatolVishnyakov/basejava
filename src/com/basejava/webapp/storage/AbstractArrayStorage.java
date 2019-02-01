package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    static final int RESUME_NOT_FOUND = -1;
    private static final int DEFAULT_CAPACITY = 10_000;
    final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    int size = 0;

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", resume.getUuid()));
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            System.out.println("Array index out of bounds.");
            return;
        }
        int index = indexOf(resume.getUuid());
        insertResume(index, resume);
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

    protected abstract int indexOf(String uuid);

    protected abstract void insertResume(int index, Resume resume);
}
