package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage<T extends Integer> extends AbstractStorage<T> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElement(T index) {
        storage.remove(index.intValue());
    }

    @Override
    protected void insertElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(T key) {
        return storage.get(key);
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected boolean isExist(Integer key) {
        return key > RESUME_NOT_FOUND;
    }

    @Override
    protected T getSearchKey(String uuid) {
        for (Integer i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return (T) i;
            }
        }
        return (T) RESUME_NOT_FOUND;
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
