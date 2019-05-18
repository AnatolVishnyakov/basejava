package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.XmlStreamStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY.toString(), new XmlStreamStrategy()));
    }
}