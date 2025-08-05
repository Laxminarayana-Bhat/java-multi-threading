package org.example;

public class DeadlyLock {

    //| Condition        | Description                                               |
    //| ---------------- | --------------------------------------------------------- |
    //| Mutual Exclusion | Only one thread can hold a resource at a time             |
    //| Hold and Wait    | Threads hold some resources and wait for others           |
    //| No Preemption    | Resources cannot be forcibly taken from a thread          |
    //| Circular Wait    | Threads form a cycle of waiting on each other’s resources |

    //to create let 1 thread catch lock and waits , then another cathes the 2 and waits, after waiting 1 try to catch 2 but will wait.....

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " acquired lock1 in methodA");

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " acquired lock2 in methodA");
            }
        }
    }

    public void methodB() {
        synchronized (lock2) {//use lock1 to solve deadlock
            System.out.println(Thread.currentThread().getName() + " acquired lock2 in methodB");

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock1) {//use lock2 to solve deadlock
                System.out.println(Thread.currentThread().getName() + " acquired lock1 in methodB");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        DeadlyLock deadlyLock=new DeadlyLock();
        Thread t1 = new Thread(deadlyLock::methodA, "Thread-1");
        Thread t2 = new Thread(deadlyLock::methodB, "Thread-2");

        t1.start();
        t2.start();
    }

    //| Strategy                                       | Breaks           | How                               |
    //| ---------------------------------------------- | ---------------- | --------------------------------- |
    //| Use concurrent collections / immutable objects | Mutual Exclusion | Make resources shareable          |
    //| Acquire all locks up front                     | Hold and Wait    | Prevent waiting while holding     |
    //| Try-lock + backoff                             | No Preemption    | Voluntarily release locks         |
    //| Global lock ordering                           | Circular Wait    | Avoid cyclical dependencies       |
    //| Detect and recover                             | All (reactively) | Use monitoring or management APIs |
}
