package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StarvationEx {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread fast=new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    System.out.println("Fast thread got the guard");
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread slow=new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    System.out.println("Slow thread got the guard");
                    Thread.sleep(100); // makes slow thread slower
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        fast.start();
        slow.start();

        //Its just an example, its not guaranteed that the thread get starved

    }

}
