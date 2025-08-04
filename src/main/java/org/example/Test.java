package org.example;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        /*----------------SYNC-----------------*/
//        Counter counter = new Counter();
//        Thread t1 = new MyThread(counter);//same instance
//        Thread t2 = new MyThread(counter);//same instance, we are sharing resources here
//        //race condition will occur if its not synchronized
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        System.out.println(counter.getCount());

        /*--------------------LOCKS------------------*/
        BankAccount bankAccount = new BankAccount();
        Runnable runnable = () -> {
            bankAccount.withdraw(50);
        };
        Thread b1=new Thread(runnable,"T b1");
        Thread b2=new Thread(runnable,"T b2");
        b1.start();
        b2.start();

    }
}
