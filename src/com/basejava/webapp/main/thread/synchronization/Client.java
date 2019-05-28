package com.basejava.webapp.main.thread.synchronization;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(100_000);
        System.out.println("Begin balance = " + account.getBalance());

        Thread withdrawThread = new WithdrawThread(account);
        Thread depositThread = new DepositThread(account);
        withdrawThread.start();
        depositThread.start();

        withdrawThread.join();
        depositThread.join();

        System.out.println("End balance = " + account.getBalance());
    }
}
