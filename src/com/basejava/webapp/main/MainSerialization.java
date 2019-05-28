package com.basejava.webapp.main;

import java.io.*;

class Person implements Serializable {
    private static final long serialVersionUID = -1878766381663668881L;
    private transient int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

class WriteObject {
    public static void main(String[] args) {
        Person person1 = new Person(1, "Ivanov");
        Person person2 = new Person(2, "Petrov");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("D:\\IdeaProjects\\basejava\\test\\resources\\people.bin"))) {
            outputStream.writeObject(person1);
            outputStream.writeObject(person2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadObject {
    public static void main(String[] args) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\IdeaProjects\\basejava\\test\\resources\\people.bin"))) {
            Person person1 = (Person) inputStream.readObject();
            Person person2 = (Person) inputStream.readObject();

            System.out.println(person1);
            System.out.println(person2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class MainSerialization {
}
