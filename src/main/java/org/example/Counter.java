package org.example;

public class Counter {

    private volatile int count = 0;

    public int getCount() {
        return count;
    }

    //synchronized method
    //critical section can be called by only 1 thread at a time - mutual exclusion
    //intrinsic lock - automatic locking by system
    public void increment() {
        count++;
    }

    //synchronized method

//    public void increment(){
//        synchronized (this){
//            count++;
//        }
//    }

//    public AtomicInteger count=new AtomicInteger(0);
//    public AtomicInteger getCount(){
//        return count;
//    }
//    public void increment(){
//        count.addAndGet(1);
//    }

    //Used  thread safe atomic integer for sync operation

}
