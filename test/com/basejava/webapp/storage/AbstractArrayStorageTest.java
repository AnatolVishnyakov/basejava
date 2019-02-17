package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    protected static final int DEFAULT_CAPACITY = 4;
    protected Storage storage;
    protected Resume[] resumes;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
        this.resumes = new Resume[]{
                new Resume("uuid1"),
                new Resume("uuid2"),
                new Resume("uuid3")
        };
    }

    @Before
    public void setUp() {
        storage.clear();
        Arrays.stream(resumes).forEach(resume -> storage.save(resume));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        assertEquals(resumes[0], storage.get("uuid1"));
        assertEquals(resumes[1], storage.get("uuid2"));
        assertEquals(resumes[2], storage.get("uuid3"));
    }

    @Test
    public void update() {
        storage.update(new Resume("uuid1"));
        storage.update(new Resume("uuid2"));
        storage.update(new Resume("uuid3"));
        assertEquals(resumes[0], storage.get("uuid1"));
        assertEquals(resumes[1], storage.get("uuid2"));
        assertEquals(resumes[2], storage.get("uuid3"));
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        assertEquals(4, storage.size());
        assertNotNull(storage.get("uuid4"));
    }

    @Test
    public void delete() {
        storage.delete("uuid2");
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void storageIsFull() {
        storage.save(new Resume("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.save(new Resume("uuid4"));
        storage.save(new Resume("uuid5"));
    }
}