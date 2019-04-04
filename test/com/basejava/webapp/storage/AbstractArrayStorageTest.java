package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.DEFAULT_CAPACITY; i++) {
                storage.save(new Resume(String.format("uuid-%s", i)));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("uuid-10001"));
    }
}