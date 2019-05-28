package com.basejava.webapp.main.thread.synchronization;

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
    public void deposit(long amount) {
        checkAmountNonNegative(amount);
        synchronized (this) {
            balance += amount;
        }
    }

    // Снятие денег со счета
    public synchronized void withdraw(long amount) {
        checkAmountNonNegative(amount);
        synchronized (this) {
            if (balance < amount) {
                throw new IllegalArgumentException("not enough money");
            }
            balance -= amount;
        }
    }

    private void checkAmountNonNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("negative amount");
        }
    }
}
