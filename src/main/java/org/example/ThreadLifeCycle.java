package org.example;

public class ThreadLifeCycle extends Thread {
    @Override
    public void run() {
        System.out.println("RUNNING");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new ThreadLifeCycle();
        System.out.println(thread.getState());//NEW
        thread.start();
        System.out.println(thread.getState());//RUNNABLE
        Thread.sleep(100);//main thread will sleep
        System.out.println(thread.getState());//TIMED WAITING
        //practically nothing as RUNNING state in java, jvm initiates when cpu is available for task execution
        thread.join();
        System.out.println(thread.getState());//TERMINATED

    }
}
