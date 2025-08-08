package org.example;

public class MyThread extends Thread {

    Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
    //Thread t = new Thread(() -> {
    //    // long-running task
    //});
    //t.start(); // 👈 "fork" - "Forking" a thread just means starting a new thread to do something.
    //t.join();  // 👈 "join" - Thread.join() is used to wait for another thread to finish.


    //| Situation                                  | Use                                    |
    //| ------------------------------------------ | -------------------------------------- |
    //| Only **one thread writes**, others read    | `volatile`                             |
    //| Multiple threads **read + update** a value | `AtomicInteger`, `AtomicBoolean`, etc. |

    //volatile — for visibility only, usually for primitive, threads hold the variable values in cache, so we can use volatile in that case
}
