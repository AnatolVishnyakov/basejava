package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

public class SqlStorage extends AbstractStorage {
    @Override
    protected void deleteElement(Object key) {

    }

    @Override
    protected void insertElement(Object key, Resume resume) {

    }

    @Override
    protected Resume getElement(Object key) {
        return null;
    }

    @Override
    protected void updateElement(Object key, Resume resume) {

    }

    @Override
    protected boolean isExist(Object key) {
        return false;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected List<Resume> convertToListStorage() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}
