package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {
    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    Resume[] getAll();

    int size();
}
