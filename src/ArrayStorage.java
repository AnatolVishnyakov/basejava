import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        if (!isExist(resume.uuid)) {
            if (index >= DEFAULT_CAPACITY) {
                throw new IndexOutOfBoundsException("Выход за пределы массива.");
            }
            storage[index++] = resume;
        }
    }

    /**
     * Получение резюме по UUID
     *
     * @param uuid String
     * @return Resume
     */
    Resume get(String uuid) {
        if (isExist(uuid)) {
            Resume foundResume = streamStorage()
                    .filter(r -> r.uuid == uuid)
                    .findFirst()
                    .get();
            return foundResume;
        }

        System.out.println(String.format("Резюме {%s} не найдено.", uuid));
        return null;
    }

    /**
     * Удаление резюме по uuid
     *
     * @param uuid String
     */
    void delete(String uuid) {
        if (isExist(uuid)) {
            int indexRemoveElement = IntStream.range(0, index)
                    .filter(i -> storage[i].uuid == uuid)
                    .findFirst()
                    .getAsInt();

            int numMoved = index - indexRemoveElement - 1;
            if (numMoved > 0) {
                System.arraycopy(storage, indexRemoveElement + 1, storage, indexRemoveElement, numMoved);
            }
            storage[--index] = null; // clear to let GC do its work
            System.out.println(String.format("Резюме {%s} удалено...", uuid));
        } else {
            System.out.println(String.format("Резюме {%s} не найдено...", uuid));
        }
    }

    /**
     * Обновление резюме
     *
     * @param resume Resume
     */
    void update(Resume resume) {
        if (isExist(resume.uuid)) {
            Resume foundResume = get(resume.uuid);
            foundResume = resume;
            System.out.println(String.format("Резюме {%s} обновлено...", resume.uuid));
        } else {
            System.out.println(String.format("Резюме {%s} не найдено...", resume.uuid));
        }
    }

    /**
     * Проверка на существование резюме
     *
     * @param uuid Resume
     * @return boolean
     */
    boolean isExist(String uuid) {
        return streamStorage().anyMatch(r -> r.uuid == uuid);
    }

    Stream<Resume> streamStorage() {
        return Arrays.stream(storage).limit(index);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    int size() {
        return index;
    }
}
