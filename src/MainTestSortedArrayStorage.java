import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;
import com.basejava.webapp.storage.Storage;

import java.util.Arrays;

public class MainTestSortedArrayStorage {
    private final static Storage STORAGE = new SortedArrayStorage();
    private final static String TEST_UUID = "uuid5,uuid1,uuid1,uuid4,uuid6,uuid2,uuid8";

    private static Resume newResume(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return resume;
    }

    private static void printAll() {
        System.out.print("PRINT ALL: ");
        Arrays.stream(STORAGE.getAll())
                .forEach(r -> System.out.print(r.getUuid() + " "));
    }

    private static void testMethodSave() {
        System.out.println(String.format("\nADD: %s", TEST_UUID));
        Arrays.stream(TEST_UUID.split(","))
                .forEach(uuid -> STORAGE.save(newResume(uuid)));
        System.out.println("RESULT: ");
        printAll();
    }

    private static void testMethodGet() {
        System.out.println("\nGET: ");
        Arrays.stream(TEST_UUID.split(","))
                .forEach(uuid -> System.out.println(String.format("get %s: %s", uuid, STORAGE.get(uuid).getUuid())));
    }

    private static void testMethodUpdate() {
        System.out.println("\nUPDATE: ");
        Resume oldResume = STORAGE.get("uuid5");
        STORAGE.update(newResume("uuid5"));
        Resume newResume = STORAGE.get("uuid5");
        System.out.println(String.format("oldResume == newResume : %s\n", oldResume == newResume));
    }

    private static void testMethodDelete() {
        final String deleteUuid = "uuid4";
        System.out.println("\nDELETE: " + deleteUuid);
        STORAGE.delete(deleteUuid);
        printAll();
    }

    public static void main(String[] args) {
        testMethodSave();
        testMethodGet();
        testMethodUpdate();

        printAll();
        testMethodDelete();
    }
}
