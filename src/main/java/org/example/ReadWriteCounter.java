package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCounter {

    private int count = 0;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    //if write lock is acquired read lock cant be acquired

    public void increment() {
        writeLock.lock();
        try {
            count++;
//            Thread.sleep(50);//letting other threads to read
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteCounter readWriteCounter = new ReadWriteCounter();

        Runnable reads = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(readWriteCounter.getCount() + " READ THREAD- " + Thread.currentThread().getName());
            }
        };

        Runnable writes = () -> {
            for (int i = 0; i < 10; i++) {
                readWriteCounter.increment();
                System.out.println(" WRITE THREAD- " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(reads);
        Thread t2 = new Thread(writes);
        Thread t3 = new Thread(reads);
        t2.start();
        t3.start();
        t1.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("DONE" + readWriteCounter.getCount());

        //with sleep
        //WRITE THREAD- Thread-1
        //1 READ THREAD- Thread-0
        //1 READ THREAD- Thread-2
        // WRITE THREAD- Thread-1
        //2 READ THREAD- Thread-2
        //2 READ THREAD- Thread-0

        //without sleep
        //WRITE THREAD- Thread-1
        // WRITE THREAD- Thread-1
        //...
        //0 READ THREAD- Thread-0
        //0 READ THREAD- Thread-2
        //10 READ THREAD- Thread-0
        //10 READ THREAD- Thread-2
    }
}
