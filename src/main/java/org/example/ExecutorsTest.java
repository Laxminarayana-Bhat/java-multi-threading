package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorsTest {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(9);//max size of n
    private static final ExecutorService single = Executors.newSingleThreadExecutor();//single thread
    Executor executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        for (int i = 0; i < 15; i++) {
            int finalI = i;
            executorService.submit(() -> {//submit take 3 type of params (callable, runnable, runnable with result if success)
                long time = fact(finalI);
                System.out.println(time);
            });//submit is used to create runnable methods
        }

        System.out.println("---------------------");


        for (int i = 0; i < 15; i++) {
            int finalI = i;
            Future<Long> future = executorService.submit(() -> fact(finalI));//result of an asynchronous computation
//            future.get(1,TimeUnit.SECONDS);//waits for 1 second if didnt get within the specified time, throws exception
//            future.cancel(true);//cancels the future true-interrupts, false- doesnt interrupt
//            future.isCancelled();//returns true if cancelled
            System.out.println(future.get());
            if (future.isDone()) {
                System.out.println("DONE");
            }

        }
        executorService.shutdown();//closes the service
        while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {//Blocks until all tasks have completed execution after
            // a shutdown request, or the timeout occurs, or the current thread is interrupted, whichever happens first
            System.out.println("Waiting!!!");
        }

        //invokeall method - takes collections, returns list of future
        Callable<Integer> c1 = () -> {
            System.out.println("task1");
            Thread.sleep(1001);
            return 1;
        };
        Callable<Integer> c2 = () -> 2;
        Callable<Integer> c3 = () -> 3;

        List<Future<Integer>> futureList = single.invokeAll(Arrays.asList(c1, c2, c3), 1, TimeUnit.SECONDS);

        for (Future<Integer> f : futureList) {
            System.out.println(f.get());
        }
        single.shutdown();


        //------------------scheduled executors
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(
                () -> System.out.println("prints after 2 seconds"), 2, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(
                () -> System.out.println("prints after every 2 seconds"), 2, 2, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(
//                () -> System.out.println("prints after every 2 seconds"), 2, 2, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> {
            System.out.println("Shutdown after 10 seconds, else it will be infinite loop");
            scheduledExecutorService.shutdown();
        }, 10, TimeUnit.SECONDS);

        //-------------------------Cached thread pool
//        ExecutorService executorServiceCache = Executors.newCachedThreadPool();

        //---Countdown latch
        //!!! can be used inside the constructor of executors service
        CountDownLatch countDownLatch = new CountDownLatch(3);//allows one or more threads to wait until a set of operations being performed in other threads completes
        countDownLatch.countDown();//Decrements the count of the latch, releasing all waiting threads if the count reaches zero.
        //after the countdown latch will be unlocked below
        countDownLatch.await();//Causes the current thread to wait until the latch has counted down to zero, unless the thread is interrupted.

        //its non reusable and blocks main thread
        //------------- so we can use Cyclic barrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            System.out.println("inside");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        });
        es.shutdown();


    }

    public static long fact(int n) {
        if (n < 1) return 1;
        String s = "";
        for (int i = 0; i < 100; i++) {
            s += "a";
        }
        return n * fact(n - 1);
    }
}
//| Feature              | `Runnable`                  | `Callable<V>`                     |
//| -------------------- | --------------------------- | --------------------------------- |
//| Returns result?      | ❌ No                        | ✅ Yes (`V` type)                  |
//| Can throw exception? | ❌ Only unchecked exceptions | ✅ Can throw checked exceptions    |
//| Used with?           | `Thread`, `ExecutorService` | `ExecutorService` (with `submit`) |
//| Return type          | `void`                      | Any type (`V`)                    |
//| Method to override   | `run()`                     | `call()`                          |


//| Feature             | **CountDownLatch**                                                          | **CyclicBarrier**                                                     |
//| ------------------- | --------------------------------------------------------------------------- | --------------------------------------------------------------------- |
//| **Type**            | One-time use                                                                | Reusable (cyclic)                                                     |
//| **Purpose**         | Wait until a specific number of events occur (e.g., threads finish)         | Wait until a specific number of threads reach a common point          |
//| **Thread waiting**  | One or more threads wait                                                    | All threads wait for each other                                       |
//| **Reset/reuse**     | ❌ Cannot be reset                                                           | ✅ Can be reused after the barrier is broken                           |
//| **Implemented via** | `CountDownLatch`                                                            | `CyclicBarrier`                                                       |
//| **Main method**     | `countDown()`, `await()`                                                    | `await()`                                                             |
//| **Best use case**   | Wait for threads to finish before continuing (e.g., main waits for workers) | Make multiple threads wait until all are ready (e.g., start together) |
