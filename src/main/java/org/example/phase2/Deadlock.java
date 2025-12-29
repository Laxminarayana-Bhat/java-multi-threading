package org.example.phase2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    final Object res1 = new Object();
    final Object res2 = new Object();

    static Lock l1 = new ReentrantLock();
    static Lock l2 = new ReentrantLock();

    public void fun1() {
        synchronized (res1) {
            System.out.println("Lock on resource 1");
            synchronized (res2) {
                System.out.println("Lock on res 2");
            }
        }
    }

    public void fun2() {
        synchronized (res2) {
            System.out.println("Lock on resource 2");
            synchronized (res1) {
                System.out.println("Lock on res 1");
            }
        }
    }

    public static void main(String[] args) {
        Deadlock d = new Deadlock();
        Thread t1 = new Thread(d::fun1);
        Thread t2 = new Thread(d::fun2);
//        t1.start();
//        t2.start();
        //Deadlock

        Thread t3 = new Thread(() -> {
            l1.lock();
            try {
                System.out.println("L1 lock");
                l2.lock();
                try {
                    System.out.println("L2 lock");
                } finally {
                    l2.unlock();
                }
            } finally {
                l1.unlock();
            }

        });
        Thread t4 = new Thread(() -> {
            l2.lock();
            try {
                System.out.println("L2 lock");
                //add delay if needed
                l1.lock();
                try {
                    System.out.println("L1 lock");
                } finally {
                    l1.unlock();
                }
            } finally {
                l2.unlock();
            }
        });
        t3.start();
        t4.start();
    }
}
