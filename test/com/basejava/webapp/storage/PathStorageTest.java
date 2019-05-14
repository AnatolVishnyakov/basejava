package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY.toString(), new ObjectStreamStrategy()));
    }
}