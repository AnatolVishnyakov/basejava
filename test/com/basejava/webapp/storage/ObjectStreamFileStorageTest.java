package com.basejava.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(PATH_STORAGE_DIRECTORY));
    }
}