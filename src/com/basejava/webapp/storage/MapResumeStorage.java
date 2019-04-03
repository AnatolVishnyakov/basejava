package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void deleteElement(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected void insertElement(Object key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object resume) {
        return resume instanceof Resume
                ? storage.get(((Resume) resume).getUuid())
                : null;
    }

    @Override
    protected void updateElement(Object key, Resume resume) {
        storage.put(((Resume) key).getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume instanceof Resume &&
                storage.containsKey(((Resume) resume).getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storageList = new ArrayList<>(storage.values());
        Collections.sort(storageList, RESUME_COMPARATOR);
        return storageList;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
