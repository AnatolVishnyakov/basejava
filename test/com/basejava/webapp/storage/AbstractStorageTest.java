package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class AbstractStorageTest {
    protected static final File PATH_STORAGE_DIRECTORY = Config.getInstance().getStorageDirectory();
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
    public void emptyContacts() {
        storage.clear();
        storage.save(RESUME_5);
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(1, resumes.size());
        resumes.forEach(resume -> assertEquals(0, resume.getContacts().size()));
    }

    @Test
    public void update() {
        Resume expectedResume = new Resume(UUID_1, "Francisco Domingo Carlos Andres Sebastian D'anconia");
        expectedResume.setContact(ContactType.WEBSITE, "www.testsite.com");
        expectedResume.setContact(ContactType.EMAIL, "test@testsite.com");
        storage.update(expectedResume);

        Resume actualResume = storage.get(UUID_1);
        assertEquals(expectedResume, actualResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test
    public void getIfEmptyContacts() {
        RESUME_1.getContacts().clear();
        assertEquals(0, RESUME_1.getContacts().size());
        assertNull(RESUME_1.getContact(ContactType.WEBSITE));
    }
}
