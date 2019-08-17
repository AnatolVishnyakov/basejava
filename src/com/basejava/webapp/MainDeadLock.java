package com.basejava.webapp;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadResourceA extends Thread {
    private final String lockOne;
    private final String lockTwo;

    public ThreadResourceA(String lockOne, String lockTwo) {
        this.lockOne = lockOne;
        this.lockTwo = lockTwo;
    }

    public void run() {
        synchronized (lockOne) {
            System.out.println("ThreadResourceA: Захватил " + lockOne);

            System.out.println("ThreadResourceA: Ожидает освобождения " + lockTwo);

            synchronized (lockTwo) {
                System.out.println("ThreadResourceA: Захватил " + lockOne + " и " + lockTwo);
            }
        }
    }
}

class ThreadResourceB extends Thread {
    private final String lockOne;
    private final String lockTwo;

    public ThreadResourceB(String lockOne, String lockTwo) {
        this.lockOne = lockOne;
        this.lockTwo = lockTwo;
    }

    public void run() {
        synchronized (lockTwo) {
            System.out.println("ThreadResourceB: Захватил " + lockTwo);

            System.out.println("ThreadResourceB: Ожидает освобождения " + lockOne);

            synchronized (lockOne) {
                System.out.println("ThreadResourceB: Захватил " + lockOne + " и " + lockTwo);
            }
        }
    }
}

public class MainDeadLock {
    private static final String LOCK_ONE = "LOCK_ONE";
    private static final String LOCK_TWO = "LOCK_TWO";

    public static void main(String args[]) throws InterruptedException {
        ThreadResourceA t1 = new ThreadResourceA(LOCK_ONE, LOCK_TWO);
        ThreadResourceB t2 = new ThreadResourceB(LOCK_ONE, LOCK_TWO);

        Thread.sleep(1_000);

        t1.start();
        t2.start();
    }
}

class App {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void log(String text) {
        String name = Thread.currentThread().getName(); //like Thread-1 or Thread-0
        String color = ANSI_BLUE;
        int val = Integer.valueOf(name.substring(name.lastIndexOf("-") + 1)) + 1;
        if (val != 0) {
            color = ANSI_PURPLE;
        }
        System.out.println(color + name + ": " + text + color);
        try {
            System.out.println(color + name + ": wait for " + val + " sec" + color);
            Thread.currentThread().sleep(val * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lock first = new ReentrantLock();
        Lock second = new ReentrantLock();

        Runnable locker = () -> {
            boolean firstLocked = false;
            boolean secondLocked = false;
            try {
                while (!firstLocked || !secondLocked) {
                    firstLocked = first.tryLock(100, TimeUnit.MILLISECONDS);
                    log("First Locked: " + firstLocked);
                    secondLocked = second.tryLock(100, TimeUnit.MILLISECONDS);
                    log("Second Locked: " + secondLocked);
                }
                first.unlock();
                second.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(locker).start();
        new Thread(locker).start();
    }
}