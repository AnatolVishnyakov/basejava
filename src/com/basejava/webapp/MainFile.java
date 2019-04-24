package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    private static final File ROOT_PROJECT_FOLDER = new File("./");

    private static void walkDirectoryTree(File rootProjectFolder) {
        File[] files = Objects.requireNonNull(rootProjectFolder.listFiles());
        for (File file : files) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());
            } else {
                walkDirectoryTree(file);
            }
        }
    }

    public static void main(String[] args) {
        walkDirectoryTree(ROOT_PROJECT_FOLDER);
    }
}
