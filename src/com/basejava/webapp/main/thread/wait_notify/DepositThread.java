package com.basejava.webapp.main.thread.wait_notify;

public class DepositThread extends Thread {
    private final Account account;

    public DepositThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50_000_000; i++) {
            account.deposit(1);
        }
    }
}
