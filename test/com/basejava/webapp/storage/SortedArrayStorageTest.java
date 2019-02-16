package com.basejava.webapp.storage;

import org.junit.BeforeClass;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    @BeforeClass
    public static void initializeStorage() {
        storage = new SortedArrayStorage();
    }
}