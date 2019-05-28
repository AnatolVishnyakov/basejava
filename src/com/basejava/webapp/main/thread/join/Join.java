package com.basejava.webapp.main.thread.join;

public class Join extends Thread {
    @Override
    public void run() {
        System.out.println("1. print");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Join();
        thread.start();
//        thread.join();
        System.out.println("2. print");
    }
}
