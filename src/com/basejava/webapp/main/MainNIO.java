package com.basejava.webapp.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainNIO {
    private static void testAbsolutePath() {
        // Cоздание объекта Path через вызов статического метода get() класса Paths
//        Path testFilePath = Paths.get("/home/heorhi/testfile.txt");

        //Пример строки создания объекта Path пути для запуска в Windows
        Path testFilePath = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");

        //Вывод инормации о файле
        System.out.println("Printing file information: ");
        System.out.println("\t file name: " + testFilePath.getFileName());
        System.out.println("\t root of the path: " + testFilePath.getRoot());
        System.out.println("\t parent of the target: "
                + testFilePath.getParent());

        //Вывод элементов пути
        System.out.println("Printing elements of the path: ");
        for (Path element : testFilePath) {
            System.out.println("\t path element: " + element);
        }
    }

    private static void testRelativePath() throws IOException {
//        Path testFilePath = Paths.get("./Test");

        //Пример строки пути для запуска в Windows
        Path testFilePath = Paths.get(".\\Test");

        System.out.println("The file name is: " + testFilePath.getFileName());
        System.out.println("It's URI is: " + testFilePath.toUri());
        System.out.println("It's absolute path is: "
                + testFilePath.toAbsolutePath());
        System.out.println("It's normalized path is: "
                + testFilePath.normalize());

        //Получение другого объекта строки по нормализованному относительному пути
        Path testPathNormalized = Paths
                .get(testFilePath.normalize().toString());
        System.out.println("It's normalized absolute path is: "
                + testPathNormalized.toAbsolutePath());
        System.out.println("It's normalized real path is: "
                + testFilePath.toRealPath(LinkOption.NOFOLLOW_LINKS));
    }

    private static void testIsSameFile() throws IOException {
        Path path1 = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");
        Path path2 = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");

        System.out.println(Files.isSameFile(path1, path2));
    }

    private static void testIsExistFile() {
        //Проверка для файла
//        Path path = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");

        //Проверка для директории
        Path path = Paths.get("D:\\IdeaProjects\\basejava\\storage");

        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            System.out.println("The file/directory " + path.getFileName() + " exists");
            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                System.out.println(path.getFileName() + " is a directory");
            } else {
                System.out.println(path.getFileName() + " is a file");
            }
        } else {
            System.out.println("The file/directory " + path.getFileName() + " does not exist");
        }
    }

    private static void testFlagsFile() {
        Path path = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");

        System.out.println(
                String.format("Readable: %s\nWritable: %s\nExecutable: %s",
                        Files.isReadable(path), Files.isWritable(path), Files.isExecutable(path))
        );
    }

    private static void testAttributeFile() throws IOException {
        Path path = Paths.get("D:\\IdeaProjects\\basejava\\storage\\uuid1");

        Object attribute = Files.getAttribute(path, "creationTime");
        System.out.println("Creation time: " + attribute);

        //Здесь указан третий параметр
        attribute = Files.getAttribute(path, "lastModifiedTime",
                LinkOption.NOFOLLOW_LINKS);
        System.out.println("Last modified time: " + attribute);

        attribute = Files.getAttribute(path, "size");
        System.out.println("Size: " + attribute);

        attribute = Files.getAttribute(path, "isDirectory");
        System.out.println("isDirectory: " + attribute);

//        attribute = Files.getAttribute(path, "owner");
//        System.out.println("Owner: " + attribute);
    }

    public static void main(String[] args) throws IOException {
        testAttributeFile();
    }
}
