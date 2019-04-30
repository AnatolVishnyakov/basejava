package com.basejava.webapp;

import java.io.*;
import java.util.Objects;

public class MainFile {
    private static final File ROOT_PROJECT_FOLDER = new File("./");
    private static String SEPARATOR = " ";

    private static void walkDirectoryTree(File rootProjectFolder, String separator) {
        File[] files = Objects.requireNonNull(rootProjectFolder.listFiles());
        for (File file : files) {
            if (!file.isDirectory()) {
                System.out.println(String.format("%s <FILE> %s", separator, file.getName()));
            } else {
                System.out.println("<FOLDER> " + file.getName());
                walkDirectoryTree(file, SEPARATOR.concat(separator));
            }
        }
    }

    private static void testByteArrayInputStream() {
        byte[] bytes = {1, -1, 0};

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        int readInt = inputStream.read();
        System.out.println(readInt);

        readInt = inputStream.read();
        System.out.println(readInt + " -> (byte) " + ((byte) readInt));

        readInt = inputStream.read();
        System.out.println(readInt);
    }

    private static void testByteArrayOutputStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(1);
        outputStream.write(-1);
        outputStream.write(0);

        byte[] bytes = outputStream.toByteArray();
        System.out.println(bytes[0] + " " + bytes[1] + " " + bytes[2]);
    }

    private static void testFileInputOutputStream() {
        byte[] bytesToWrite = {1, 2, 3, 5, 6, 7, 9, 0, 10};
        byte[] bytesReaded = new byte[10];
        String fileName = "D:\\output.txt";

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            System.out.println("Файл открыт для записи...");

            outputStream.write(bytesToWrite);
            System.out.println("Записано: " + bytesToWrite.length + " байт");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            System.out.println("Файл открыт для чтения...");

            int bytesAvailable = inputStream.available();
            System.out.println("Готово к считыванию: " + bytesAvailable + " байт");

            int count = inputStream.read(bytesReaded, 0, bytesAvailable);
            System.out.println("Считано: " + count + " байт");

            for (int i = 0; i < count; i++) {
                System.out.print(bytesReaded[i] + ", ");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testPipedInputOutputStream() {
        int countRead = 0;
        byte[] toRead = new byte[100];

        int countWrite = 0;
        try (PipedInputStream inputStream = new PipedInputStream();
             PipedOutputStream outputStream = new PipedOutputStream(inputStream)) {

            while (countRead < toRead.length) {
                for (int i = 0; i < (Math.random() * 10); i++) {
                    outputStream.write((byte) (Math.random() * 127));
                    countWrite++;
                }

                int willRead = inputStream.available();
                if (willRead + countRead > toRead.length) {
                    willRead = toRead.length - countRead;
                }
                countRead += inputStream.read(toRead, countRead, willRead);
            }
            System.out.println("Записано: " + countWrite + " байт");
            System.out.println("Считано: " + countRead + " байт");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static void testSequenceInputStream() {
        try (FileInputStream inputStreamOne = new FileInputStream("D:\\test_one.txt");
             FileInputStream inputStreamTwo = new FileInputStream("D:\\test_two.txt");
             SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStreamOne, inputStreamTwo);
             FileOutputStream outputStream = new FileOutputStream("D:\\output.txt"))
        {
            int readedByte = sequenceInputStream.read();
            while (readedByte != -1) {
                outputStream.write(readedByte);
                readedByte = sequenceInputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        walkDirectoryTree(ROOT_PROJECT_FOLDER, SEPARATOR);
//        testByteArrayInputStream();
//        testByteArrayOutputStream();
//        testFileInputOutputStream();
//        testPipedInputOutputStream();
//        testSequenceInputStream();
    }
}
