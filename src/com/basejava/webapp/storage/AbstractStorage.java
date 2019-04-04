package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public void delete(String uuid) {
        T key = getSearchKeyIfExistResume(uuid);
        deleteElement(key);
    }

    @Override
    public Resume get(String uuid) {
        T key = getSearchKeyIfExistResume(uuid);
        return getElement(key);
    }

    @Override
    public void save(Resume resume) {
        T key = getSearchKeyIfNotExistResume(resume.getUuid());
        insertElement(key, resume);
    }

    @Override
    public void update(Resume resume) {
        T key = getSearchKeyIfExistResume(resume.getUuid());
        updateElement(key, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storageList = convertToListStorage();
        storageList.sort(RESUME_COMPARATOR);
        return storageList;
    }

    protected abstract void deleteElement(T key);

    protected abstract void insertElement(T key, Resume resume);

    protected abstract Resume getElement(T key);

    protected abstract void updateElement(T key, Resume resume);

    protected abstract boolean isExist(T key);

    protected abstract T getSearchKey(String uuid);

    protected abstract List<Resume> convertToListStorage();

    private T getSearchKeyIfExistResume(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private T getSearchKeyIfNotExistResume(String uuid) {
        T key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}
