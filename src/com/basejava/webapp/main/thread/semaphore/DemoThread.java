package com.basejava.webapp.main.thread.semaphore;

import java.util.concurrent.Semaphore;

class DemoThread extends Thread {
    private final Semaphore semaphore;

    DemoThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            runUnsafe();
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted");
        }
    }

    private void runUnsafe() throws InterruptedException {
        for (;;) {
            semaphore.acquire();
            try {
                System.out.println(getName() + " acquired semaphore");
                Thread.sleep(5000L);
            } finally {
                System.out.println(getName() + " releasing semaphore");
                semaphore.release();
            }
        }
    }
}
