package com.basejava.webapp.main.thread.count_down_latch;

import java.util.concurrent.CountDownLatch;

public class DemoThread extends Thread {
    private final CountDownLatch latch;

    public DemoThread(CountDownLatch latch) {
        this.latch = latch;
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
        Thread.sleep((long) (Math.random() * 10_000L));

        System.out.println(getName() + " finished initialization");

        latch.countDown();
        latch.await();

        System.out.println(getName() + " entered main phase");

        Thread.sleep((long) (Math.random() * 10_000L));
    }
}
