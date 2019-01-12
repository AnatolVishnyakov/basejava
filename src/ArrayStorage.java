import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int DEFAULT_CAPACITY = 10_000;
    private Resume[] storage = new Resume[DEFAULT_CAPACITY];
    private int index = 0;

    void clear() {
        index = 0;
    }

    /**
     * Сохранение резюме
     *
     * @param resume Resume
     */
    void save(Resume resume) {
        if (index >= DEFAULT_CAPACITY) {
            throw new IndexOutOfBoundsException("Выход за пределы массива.");
        }
        storage[index++] = resume;
    }

    /**
     * Получение резюме по UUID
     *
     * @param uuid String
     */
    Resume get(String uuid) {
        Resume foundResume = null;
        try {
            foundResume = Arrays.stream(storage)
                    .filter(r -> r.uuid == uuid)
                    .findFirst()
                    .get();
        } catch (Exception exc) {
            System.out.println(String.format("Резюме {%s} не найдено.", uuid));
        }
        return foundResume;
    }

    /**
     * Удаление резюме по uuid
     *
     * @param uuid String
     */
    void delete(String uuid) {
        try {
            int indexRemoveElement = IntStream.range(0, storage.length)
                    .filter(i -> storage[i].uuid == uuid)
                    .findFirst()
                    .getAsInt();

            int numMoved = index - indexRemoveElement - 1;
            if (numMoved > 0)
                System.arraycopy(storage, indexRemoveElement + 1, storage, indexRemoveElement, numMoved);
            storage[--index] = null; // clear to let GC do its work
        } catch (Exception exc) {
            System.out.println("Элемент не найден...");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (index == 0) {
            return new Resume[0];
        }
        return Arrays.copyOfRange(storage, 0, size());
    }

    int size() {
        return index;
    }
}
