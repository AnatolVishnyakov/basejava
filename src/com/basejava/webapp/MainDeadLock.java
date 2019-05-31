package com.basejava.webapp;

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