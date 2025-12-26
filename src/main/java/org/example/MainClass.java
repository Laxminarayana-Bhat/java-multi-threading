package org.example;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        t1.start();
        t2.start();//tried with volatile also , still it fails as it doesn't guarantee automaticity
        Thread t3 = new Thread(() ->
        {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }
        );
        t3.start();
        t3.join();
        t1.join();
        t2.join();
        System.out.println(counter.getCount());

        //Example for race condition

    }
}
