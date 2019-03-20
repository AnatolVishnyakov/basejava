package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static final int RESUME_NOT_FOUND = -1;

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElementByIndex(index);
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(uuid);
        }
        return getElementByIndex(index);
    }

    @Override
    public void save(Resume resume) {
        beforeSaveCallback(resume);
        int index = indexOf(resume.getUuid());
        if (index > RESUME_NOT_FOUND) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElementByIndex(index, resume);
        }
        afterSaveCallback();
    }

    @Override
    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index <= RESUME_NOT_FOUND) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElementByIndex(index, resume);
        }
    }

    protected abstract void afterSaveCallback();

    protected abstract void beforeSaveCallback(Resume resume);

    protected abstract void deleteElementByIndex(int index);

    protected abstract Resume getElementByIndex(int index);

    protected abstract int indexOf(String uuid);

    protected abstract void insertElementByIndex(int index, Resume resume);

    protected abstract void updateElementByIndex(int index, Resume resume);
}
