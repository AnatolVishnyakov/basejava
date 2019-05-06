package com.basejava.webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(PATH_STORAGE_DIRECTORY));
    }
}