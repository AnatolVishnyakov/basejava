package com.basejava.webapp.storage;

import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    @BeforeClass
    public static void initialize() {
        storage = new ArrayStorage();
    }
}