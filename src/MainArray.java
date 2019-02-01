import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;
import com.basejava.webapp.storage.SortedArrayStorage;
import com.basejava.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private static Storage ARRAY_STORAGE;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static boolean isValidCommand(String[] params) throws IOException {
        if (params.length < 1 || params.length > 2) {
            System.out.println("Неверная команда.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        boolean flag = true;
        while (flag) {
            System.out.print("Выберите вариант хранилища - (ArrayStorage | SortedArrayStorage) (A|S): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (!isValidCommand(params)) {
                continue;
            }
            if (params[0].toUpperCase().equals("A")) {
                ARRAY_STORAGE = new ArrayStorage();
                flag = false;
            } else if (params[0].toUpperCase().equals("S")) {
                ARRAY_STORAGE = new SortedArrayStorage();
                flag = false;
            } else {
                System.out.println("Неверная команда.");
            }
        }

        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | update uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (!isValidCommand(params)) {
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume();
                    r.setUuid(uuid);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                case "update":
                    r = new Resume();
                    r.setUuid(uuid);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}