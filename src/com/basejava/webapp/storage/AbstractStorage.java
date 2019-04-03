package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage<T> implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public void delete(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(key);
        }
    }

    @Override
    public Resume get(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(key);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        T key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(key, resume);
        }
    }

    @Override
    public void update(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        if (!isExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(key, resume);
        }
    }

    protected abstract void deleteElement(T key);

    protected abstract void insertElement(T key, Resume resume);

    protected abstract Resume getElement(T key);

    protected abstract void updateElement(T key, Resume resume);

    protected abstract boolean isExist(T key);

    protected abstract T getSearchKey(String uuid);
}
