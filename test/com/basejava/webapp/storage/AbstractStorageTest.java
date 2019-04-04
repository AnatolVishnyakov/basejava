package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected static final Resume RESUME_1 = new Resume("uuid1", "Ivanov Ivan Ivanovich");
    protected static final Resume RESUME_2 = new Resume("uuid2", "Petrov Petr Petrovich");
    protected static final Resume RESUME_3 = new Resume("uuid3", "Ivanov Ivan Ivanovich");
    protected static final Resume RESUME_4 = new Resume("uuid4", "Sidorov Ivan Ivanovich");
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
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
    public void getAllSorted() {
        List<Resume> actualValue = storage.getAllSorted();
        assertEquals(3, actualValue.size());
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        expected.sort(RESUME_COMPARATOR);

        assertEquals(expected, actualValue);
    }

    @Test
    public void update() {
        Resume expectedResume = new Resume("uuid1");
        expectedResume.setFullName("Francisco Domingo Carlos Andres Sebastian D'anconia");
        storage.update(expectedResume);

        Resume actualResume = storage.get("uuid1");
        assertSame(expectedResume, actualResume);
        assertEquals(expectedResume.getFullName(), actualResume.getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertSame(RESUME_4, storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        assertEquals(2, storage.size());
        storage.get("uuid2");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get("uuid1"));
        assertEquals(RESUME_2, storage.get("uuid2"));
        assertEquals(RESUME_3, storage.get("uuid3"));
    }
}
