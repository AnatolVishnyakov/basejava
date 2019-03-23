package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage<K,V> implements Storage {
    protected static final Integer RESUME_NOT_FOUND = -1;

    @Override
    public void delete(String uuid) {
        V value = getSearchKey(uuid);
        if (!isExist(value)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(value);
        }
    }

    public Resume get(String uuid) {
        V value = getSearchKey(uuid);
        if (!isExist(value)) {
            throw new NotExistStorageException(uuid);
        }
        return indexOf(value);
    }

    @Override
    public void save(Resume resume) {
        V value = getSearchKey(resume.getUuid());
        if (isExist(value)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement((K) resume.getUuid(), resume);
        }
    }

    @Override
    public void update(Resume resume) {
        V value = getSearchKey(resume.getUuid());
        if (!isExist(value)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(value, resume);
        }
    }

    protected abstract void deleteElement(V key);

    protected abstract void insertElement(K key, Resume resume);

    protected abstract Resume indexOf(V key);

    protected abstract boolean isExist(V key);

    protected abstract void updateElement(V key, Resume resume);

    protected abstract V getSearchKey(K key);
}
