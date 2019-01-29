import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;
import com.basejava.webapp.storage.SortedArrayStorage;
import com.basejava.webapp.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static Storage storage;

    private static Resume createResumeInSortedArrayStorage(Storage storage, String uuid){
        Resume resume = new Resume();
        resume.setUuid(uuid);
        storage.save(resume);
        return resume;
    }

    private static Resume createResumeInArrayStorage(Storage storage, String uuid){
        Resume resume = new Resume();
        resume.setUuid(uuid);
        storage.save(resume);
        return resume;
    }

    private static void testArrayStorageMethodSize(){
        storage = new ArrayStorage();
        System.out.println("\n:: ArrayStorage :: (create 3 objects)");
        createResumeInSortedArrayStorage(storage, "uuid2");
        createResumeInSortedArrayStorage(storage, "uuid1");
        createResumeInSortedArrayStorage(storage, "uuid3");
        printAll();
    }

    private static void testSortedArrayStorageMethodSize(){
        storage = new SortedArrayStorage();
        System.out.println("\n:: SortedArrayStorage :: (create 4 objects)");
        createResumeInSortedArrayStorage(storage, "uuid2");
        createResumeInSortedArrayStorage(storage, "uuid1");
        createResumeInSortedArrayStorage(storage, "uuid3");
        printAll();
    }

    private static void testSortedArraySave(){
        storage.clear();
        storage = new SortedArrayStorage();
        createResumeInSortedArrayStorage(storage, "uuid2");
        createResumeInSortedArrayStorage(storage, "uuid4");
        createResumeInSortedArrayStorage(storage, "uuid1");
        createResumeInSortedArrayStorage(storage, "uuid3");
        printAll();
        // uuid1
        // uuid2
        // uuid3
        // uuid4
    }

    public static void main(String[] args) {
        testArrayStorageMethodSize();
        testSortedArrayStorageMethodSize();
//        testSortedArraySave();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
    }
}
