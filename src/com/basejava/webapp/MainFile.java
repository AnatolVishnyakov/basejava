package com.basejava.webapp;

import java.io.File;

public class MainFile {
    private static final File ROOT_PROJECT_FOLDER = new File("./");

    private static void recursiveTraversal(File rootProjectFolder, String separator) {
        for (File file : rootProjectFolder.listFiles()) {
            if (file.isDirectory()) {
                System.out.println(separator + " <PACKAGE> " + file.getName());
                recursiveTraversal(new File(file.getAbsolutePath()), separator + "-");
            } else {
                System.out.println(separator + " <FILE> " + file.getName());
            }
        }
        return;
    }

    public static void main(String[] args) {
        recursiveTraversal(ROOT_PROJECT_FOLDER, "-");
    }
}
