package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] actualValue = storage.getAll();
        assertEquals(3, actualValue.length);
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, actualValue);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get("uuid1"));
        assertEquals(RESUME_2, storage.get("uuid2"));
        assertEquals(RESUME_3, storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void update() {
        Resume r1 = new Resume("uuid1");
        storage.update(r1);
        Resume r2 = new Resume("uuid2");
        storage.update(r2);
        Resume r3 = new Resume("uuid3");
        storage.update(r3);

        assertSame(r1, storage.get("uuid1"));
        assertSame(r2, storage.get("uuid2"));
        assertSame(r3, storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("uuid4"));
    }

    @Test
    public void save() {
        Resume resume4 = new Resume("uuid4");
        storage.save(resume4);
        assertEquals(4, storage.size());
        assertSame(resume4, storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid3"));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.DEFAULT_CAPACITY; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException exc) {
            fail(exc.getMessage());
        }

        storage.save(new Resume("uuid10001"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        storage.get("uuid2");
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid4");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}