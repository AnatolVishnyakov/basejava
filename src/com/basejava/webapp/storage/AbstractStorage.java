package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public void delete(String uuid) {
        LOGGER.info(String.format("Delete: %s", uuid));
        T key = getSearchKeyIfExistResume(uuid);
        deleteElement(key);
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info(String.format("Get: %s", uuid));
        T key = getSearchKeyIfExistResume(uuid);
        return getElement(key);
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info(String.format("Save: %s", resume));
        T key = getSearchKeyIfNotExistResume(resume.getUuid());
        insertElement(key, resume);
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info(String.format("Update: %s", resume));
        T key = getSearchKeyIfExistResume(resume.getUuid());
        updateElement(key, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("GetAllSorted");
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
            LOGGER.warning(String.format("Resume %s not exist", uuid));
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private T getSearchKeyIfNotExistResume(String uuid) {
        T key = getSearchKey(uuid);
        if (isExist(key)) {
            LOGGER.warning(String.format("Resume %s already exist", uuid));
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}
