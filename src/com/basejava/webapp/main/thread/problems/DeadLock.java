package com.basejava.webapp.main.thread.problems;

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

public class DeadLock {
    private static final String LOCK_ONE = "LOCK_ONE";
    private static final String LOCK_TWO = "LOCK_TWO";

    public static void main(String args[]) {
        ThreadResourceA T1 = new ThreadResourceA(LOCK_ONE, LOCK_TWO);
        ThreadResourceB T2 = new ThreadResourceB(LOCK_ONE, LOCK_TWO);

        T1.start();
        T2.start();
    }
}