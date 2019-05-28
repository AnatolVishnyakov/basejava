package com.basejava.webapp.main.thread.wait_notify;

public class Account {
    private long balance;

    public Account() {
        this(0L);
    }

    public Account(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    // Начисление денег на счет
    public synchronized void deposit(long amount) {
        checkAmountNonNegative(amount);
        balance += amount;
        notifyAll();
    }

    // Снятие денег со счета
    public synchronized void withdraw(long amount) {
        checkAmountNonNegative(amount);
        if(balance < amount) {
            throw new IllegalArgumentException("not enough money");
        }
        balance -= amount;
    }

    public synchronized void waitAndWithdraw(long amount) throws InterruptedException {
        checkAmountNonNegative(amount);
        while (balance < amount) {
            wait(100L);
            System.out.println("[Wake up] current balance: " + balance);
        }
        balance -= amount;
    }

    private void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }
}
