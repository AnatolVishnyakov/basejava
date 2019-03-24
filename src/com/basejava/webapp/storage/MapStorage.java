package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage<T extends String> extends AbstractStorage<T> {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElement(T key) {
        storage.remove(key);
    }

    @Override
    protected void insertElement(T key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected Resume getElement(T key) {
        return storage.get(key);
    }

    @Override
    protected void updateElement(T key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected boolean isExist(T key) {
        return storage.get(key) != null;
    }

    @Override
    protected T getSearchKey(String uuid) {
        return (T) uuid;
    }

    @Override
    public Resume[] getAll() {
        return storage.values()
                .toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
