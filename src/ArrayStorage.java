import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int DEFAULT_CAPACITY = 10_000;
    private Resume[] storage = new Resume[DEFAULT_CAPACITY];
    private int index = 0;

    void clear() {
    }

    /**
     * Сохранение резюме
     *
     * @param r Resume
     */
    void save(Resume r) {
        if (index >= DEFAULT_CAPACITY) {
            throw new IndexOutOfBoundsException("Выход за пределы массива");
        }
        storage[index++] = r;
    }

    /**
     * Получение резюме по UUID
     *
     * @param uuid String
     */
    Resume get(String uuid) {
        return Arrays.stream(storage)
                .filter(r -> r.uuid == uuid)
                .findFirst()
                .get();
    }

    void delete(String uuid) {

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return index;
    }
}
