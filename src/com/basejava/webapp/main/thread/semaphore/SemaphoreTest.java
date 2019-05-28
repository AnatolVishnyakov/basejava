package com.basejava.webapp.main.thread.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(2, true);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoThread thread = new DemoThread(semaphore);
            threads.add(thread);
            thread.start();
        }

        Thread.sleep(30_000);

        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}

