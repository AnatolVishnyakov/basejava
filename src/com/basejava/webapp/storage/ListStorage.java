package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage<K extends Integer, V extends Integer> extends AbstractStorage<K,V> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElement(V index) {
        storage.remove(index.intValue());
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected Resume indexOf(Integer index) {
        return storage.get(index);
    }

    @Override
    protected V getSearchKey(String uuid) {
        for (Integer i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return (V) i;
            }
        }
        return (V) RESUME_NOT_FOUND;
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isExist(Integer key) {
        return key > RESUME_NOT_FOUND;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }
}
