package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void insertElement(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null && storage.containsKey(((Resume) resume).getUuid());
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
    protected List<Resume> convertToListStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
