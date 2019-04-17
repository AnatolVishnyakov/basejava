package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_COMPARATOR);
    }

    @Override
    protected int prepareInsertPosition(int index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        return insertIndex;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
