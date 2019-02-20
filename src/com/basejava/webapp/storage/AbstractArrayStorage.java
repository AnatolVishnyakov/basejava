package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int DEFAULT_CAPACITY = 10_000;
    protected static final int RESUME_NOT_FOUND = -1;
    protected final Resume[] storage = new Resume[DEFAULT_CAPACITY];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void save(Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        int index = indexOf(resume.getUuid());
        if (index > RESUME_NOT_FOUND) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(index, resume);
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElementByIndex(index);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract int indexOf(String uuid);

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void deleteElementByIndex(int index);
}
