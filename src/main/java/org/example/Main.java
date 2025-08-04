package org.example;

public class Main {
    public static void main(String[] args) {
        CustomThread thread = new CustomThread();

        CustomRunnable runnable = new CustomRunnable();
        Thread threadRunnable = new Thread(runnable);

        threadRunnable.start();
        thread.start();

        System.out.println(Thread.currentThread().getName());
    }

    public static class CustomThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++)
                System.out.println(Thread.currentThread().getName());
        }
    }

    public static class CustomRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++)
                System.out.println(Thread.currentThread().getName());
        }
    }

    //when you use Runnable and Thread
    //lets say I have class A,B
    //i want to create class B extends class A,Thread
    //as java doesn't have multiple inheritance, so can only implement interfaces, so here i use Runnable
    //ex: class B extends class A implements Runnable{}

    /*
    - Topics covered
    - basic creation of thread (java.lang)
    - thread lifecycle
    - Thread methods like yield,priority,name,daemon,interrupt
    - synchronized for shared resources
    - Locks - intrinsic (not in our control), extrinsic (manual) -> (java.util.concurrent.locks)
     */
}
