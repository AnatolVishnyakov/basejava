package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteElement(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void insertElement(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Resume resume) {
        return resume;
    }

    @Override
    protected void updateElement(Resume searchKey, Resume resume) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
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
