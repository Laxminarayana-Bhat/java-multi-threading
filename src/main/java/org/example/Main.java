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
}
