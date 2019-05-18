package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.ObjectStreamStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY.toString(), new ObjectStreamStrategy()));
    }
}