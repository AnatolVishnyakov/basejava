package com.basejava.webapp.main.thread.count_down_latch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new DemoThread(latch).start();
        }
    }
}
