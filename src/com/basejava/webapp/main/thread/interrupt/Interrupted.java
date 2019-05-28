package com.basejava.webapp.main.thread.interrupt;

import java.util.concurrent.atomic.AtomicInteger;

public class Interrupted {
    public static void main(String[] args) {

        AtomicInteger time01 = new AtomicInteger(1_000);
        Thread thread01 = new Thread(() -> {
//            try {
//                Thread.sleep(10_000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            while (true) {
                int a = 1;
                int b = 1;
                int s = a + b;

                try {
                    System.out.println("Thread01: " + time01.get());
                    Thread.sleep(1_000);
                    time01.addAndGet(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if(Thread.interrupted()) {
                        return;
                    }
                }
            }
        });

        AtomicInteger time02 = new AtomicInteger(1_000);
        Thread thread02 = new Thread(() -> {
            while (true) {
                try {
                    if(time02.get() == 3_000) {
                        thread01.interrupt();
                        System.out.println("Thread02 is interrupted...");
                        break;
                    }
                    System.out.println("Thread02: " + time02.get());
                    Thread.sleep(time02.get());
                    time02.addAndGet(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread01.start();
        thread02.start();
    }
}
