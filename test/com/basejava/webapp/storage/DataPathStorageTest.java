package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.DataStreamStrategy;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY.toString(), new DataStreamStrategy()));
    }
}