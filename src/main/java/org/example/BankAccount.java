package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    int balance = 200;

    final Lock lock = new ReentrantLock();

    public void withdraw(int amount) {

        //tryLock() - acquire lock if its free returns true, else false (immediate or timed check)
        //void lock() - will wait until it gets the lock

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                //here only 1 thread will run as while Thread1 will hold the lock till 3 sec,
                // but Thread2 will try to acquire lock after 1 sec

                if (balance >= amount) {
                    try {
                        Thread.sleep(3000);//be careful wit the timing
                        balance -= amount;
                        System.out.println("Withdraw Done " + Thread.currentThread().getName() + " Remaining balance" + getBalance());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();//success or failure after the process remove the lock
                    }
                } else {
                    System.out.println("Insufficient balance " + Thread.currentThread().getName());
                }

            } else {
                System.out.println("Lock is not possible for " + Thread.currentThread().getName());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public int getBalance() {
        return balance;
    }

//    public synchronized void withdraw(int amount) {
//        if (amount <= balance) {
//            balance -= amount;
//            System.out.println("withdraw done, remaining balance " + getBalance() + "-" + Thread.currentThread().getName());
//
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                System.out.println("Interrupted" + e);
//            }
//            System.out.println("BALANCE" + getBalance());
//        } else {
//            System.out.println("insufficient balance " + Thread.currentThread().getName());
//        }
//    }
}
