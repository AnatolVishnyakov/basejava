package com.basejava.webapp.main.thread.join;

public class InSeriesThreads extends Thread {
    @Override
    public void run() {
        System.out.println(getName());
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            InSeriesThreads thread = new InSeriesThreads();
            thread.start();
            thread.join();
        }
    }
}
