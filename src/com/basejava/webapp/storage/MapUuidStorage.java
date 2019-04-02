package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElement(String key) {
        storage.remove(key);
    }

    @Override
    protected void insertElement(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected Resume getElement(String key) {
        return storage.get(key);
    }

    @Override
    protected void updateElement(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storageList = new ArrayList<>(storage.values());
        storageList.sort(RESUME_COMPARATOR);
        return storageList;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
