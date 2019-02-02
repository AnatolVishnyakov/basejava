package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int RESUME_NOT_FOUND = -1;
    private static final int DEFAULT_CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

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
        if (index < RESUME_NOT_FOUND) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index <= RESUME_NOT_FOUND) {
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
        if (index > RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume with uuid=%s already exists.", resume.getUuid()));
        } else {
            insertElement(index, resume);
        }
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", uuid));
        } else {
            deleteElementByIndex(index);
        }
    }

    private int indexOf(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return indexOf(resume);
    }

    private int indexOf(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void deleteElementByIndex(int index);
}
