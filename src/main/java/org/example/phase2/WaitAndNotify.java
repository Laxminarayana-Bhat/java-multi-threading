package org.example.phase2;

import java.util.LinkedList;
import java.util.Queue;

public class WaitAndNotify {

    final Object resource = new Object();

    public void waitingMethod() throws InterruptedException {
        System.out.println("Inside Waiting method");
        while (true) {
            synchronized (resource) {
                Thread.sleep(500);
                System.out.println("Still waiting");
                resource.wait();
                System.out.println("Wait is over");
                break;
            }
        }
    }

    public void notifyMethod() throws InterruptedException {
        synchronized (resource) {
            System.out.println("Inside notify Method");
            Thread.sleep(2000);
            resource.notify();
        }

    }

    public static void main(String[] args) {
        WaitAndNotify waitAndNotify = new WaitAndNotify();
        new Thread(() -> {
            try {
                waitAndNotify.waitingMethod();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                waitAndNotify.notifyMethod();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    //best example
    //producer consumer problem
    //producer will wait if no space
    //consumer will wait if buffer is empty
    static class Buffer {
        Queue<Integer> queue = new LinkedList<>();
        int capacity = 5;

        synchronized void produce(int value) throws InterruptedException {
            while (queue.size() == capacity) {
                wait(); // wait until space available
            }
            queue.add(value);
            notify();
        }

        synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait(); // wait until data available
            }
            int val = queue.poll();
            notify();
            return val;
        }
    }


}
//❌ What is going wrong in your code?
//The key rule you violated
//
//A thread must own the monitor of the object it calls wait() or notify() on.
//
//In your code:
//
//public synchronized void waitingMethod() {
//    resource.wait();   // ❌
//}
//
//public synchronized void notifyMethod() {
//    resource.notify(); // ❌
//}