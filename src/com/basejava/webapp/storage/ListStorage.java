package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElement(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null && storage.get(index) != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected List<Resume> convertToListStorage() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
