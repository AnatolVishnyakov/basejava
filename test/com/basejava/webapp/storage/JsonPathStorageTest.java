package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY.toString(), new JsonStreamStrategy()));
    }
}