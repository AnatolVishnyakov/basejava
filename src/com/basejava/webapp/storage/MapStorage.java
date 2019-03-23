package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage<K extends String, V extends String> extends AbstractStorage<K,V> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteElement(V uuid) {
        storage.remove(uuid);
    }

    @Override
    protected Resume indexOf(V uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void insertElement(K uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected boolean isExist(V key) {
        return storage.get(key) != null;
    }

    @Override
    protected void updateElement(V uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected V getSearchKey(String uuid) {
        return null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
