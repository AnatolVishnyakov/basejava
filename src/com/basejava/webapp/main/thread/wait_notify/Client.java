package com.basejava.webapp.main.thread.wait_notify;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(0);

        new DepositThread(account).start();

        System.out.println("Calling waitAndWithDraw()...");

        account.waitAndWithdraw(50_000_000);

        System.out.println("waitAndWithdraw() finished");
    }
}
