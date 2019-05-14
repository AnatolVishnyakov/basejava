package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(PATH_STORAGE_DIRECTORY, new ObjectStreamStrategy()));
    }
}