package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    private static final File ROOT_PROJECT_FOLDER = new File("./");

    private static void recursiveTraversal(File rootProjectFolder, String separator) {
        File[] files = Objects.requireNonNull(rootProjectFolder.listFiles());
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(separator + " <FOLDER> " + file.getName());
                recursiveTraversal(new File(file.getAbsolutePath()), separator + "-");
            } else {
                System.out.println(separator + " <FILE> " + file.getName());
            }
        }
    }

    public static void main(String[] args) {
        recursiveTraversal(ROOT_PROJECT_FOLDER, "-");
    }
}
