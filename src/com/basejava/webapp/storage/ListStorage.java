package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void deleteElementByIndex(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected int indexOf(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void insertElementByIndex(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateElementByIndex(int index, Resume resume) {
        storage.set(index, resume);
    }
}
