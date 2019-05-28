package com.basejava.webapp.main.thread.double_checked_locking;

public class Singleton {
    private int foo;
    private String bar;

    // Проблема double checked locking
    // решается установкой volatile
    private static volatile Singleton instance;

    private Singleton() {
        foo = 13;
        bar = "zap";
    }

    // double checked locking
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    // Проблема:
                    // ссылка на недостроенный объект будет
                    // использоваться в программе
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
