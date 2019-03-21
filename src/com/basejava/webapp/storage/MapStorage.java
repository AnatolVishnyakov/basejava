package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();
    private Set<String> keysSet = storage.keySet();

    @Override
    protected void deleteElementByIndex(int index) {
        storage.remove(keysSet.toArray()[index]);
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage.get(keysSet.toArray()[index]);
    }

    @Override
    protected int indexOf(String uuid) {
        String[] keys = storage.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(uuid)) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }

    @Override
    protected void insertElementByIndex(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElementByIndex(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
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
