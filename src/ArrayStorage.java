import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int DEFAULT_CAPACITY = 10_000;
    private static final int RESUME_NOT_FOUND = -1;
    private Resume[] storage = new Resume[DEFAULT_CAPACITY];
    private int size = 0;

    public void clear() {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                storage[i] = null;
            }
            size = 0;
        }
    }

    /**
     * Save resume
     *
     * @param resume Resume
     */
    public void save(Resume resume) {
        if (size >= DEFAULT_CAPACITY) {
            System.out.println("Array index out of bounds.");
            return;
        }
        int index = indexOf(resume.getUuid());
        if (index != RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume with uuid=%s already exists.", resume.getUuid()));
            return;
        }

        storage[size++] = resume;
        System.out.println(String.format("Resume {%s} saved.", resume.getUuid()));
    }

    /**
     * Get resume
     *
     * @param uuid String
     * @return Resume
     */
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", uuid));
            return null;
        }

        return storage[index];
    }

    /**
     * Delete resume
     *
     * @param uuid String
     */
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", uuid));
            return;
        }

        storage[index] = storage[--size];
        storage[size] = null;
    }

    /**
     * Update resume
     *
     * @param resume Resume
     */
    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index == RESUME_NOT_FOUND) {
            System.out.println(String.format("Resume {%s} not found.", resume.getUuid()));
            return;
        }
        storage[index] = resume;
    }

    private int indexOf(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
