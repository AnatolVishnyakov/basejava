package com.basejava.webapp.main.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// 1 способ: сделать nextInt метод synchronized
/*
    private static volatile int counter = 0;

    public static synchronized int nextInt() {
        return counter++;
    }
*/

// 2 способ: использовать AtomicInteger, методы
// делать синхронизированными нет необходимости
/*
    private static final AtomicInteger counter = new AtomicInteger();

    public static int nextInt() {
        return counter.getAndIncrement();
    }
*/
public class SequenceGeneratorBroken {
    private static final AtomicInteger counter = new AtomicInteger();

    public static int nextInt() {
        return counter.getAndIncrement();
    }

    private static void run() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1_000; j++) {
                    nextInt();
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int repeat = 0;
        int max = counter.get();
        while(true) {
            run();
            if(counter.get() == 10_000) {
                System.out.println("Winner, counter = " + counter + " trying: " + repeat);
                break;
            }
            if(counter.get() > max) {
                max = counter.get();
                System.out.println("counter: " + counter + " trying: " + repeat);
            }
            repeat++;
            counter.set(0);
        }

        System.out.println("Counter final value: " + counter);
    }
}