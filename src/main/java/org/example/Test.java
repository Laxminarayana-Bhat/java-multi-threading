package org.example;

public class Test {
    public static void main(String[] args) throws InterruptedException {


        Counter counter = new Counter();
        Thread t1 = new MyThread(counter);//same instance
        Thread t2 = new MyThread(counter);//same instance, we are sharing resources here
        //race condition will occur if its not synchronized
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter.getCount());
    }
}
