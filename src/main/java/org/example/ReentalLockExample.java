package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentalLockExample {

    //ReentrantLock -> If a thread already holds a lock, and it tries to acquire that same lock again,
    //                 it won't get stuck — it'll just increase an internal counter and keep going.

    //    ✅ Reentrant lock behavior:
    //    Thread A acquires the lock.
    //    Thread A (same thread) tries to acquire it again.
    //    The lock checks: “Hey, you already own this lock — go ahead.”
    //    The lock increments its hold """count""", and thread continues.
    //    Thread A must release it the same number of times.

    // !!! Otherwise, it should have been Deadlock -> methodB waits to acquire lock and mehod1 will be locked and waiting for methodB to complete XXX


    Lock lock = new ReentrantLock();//fairness -> true - everyone gets their chance

    public void methodA() throws InterruptedException {
        lock.lockInterruptibly();  // lock hold count = 1
        try {
            System.out.println("outer");
            methodB();             // lock hold count = 2
        } finally {
            lock.unlock();         // Releases once; methodB must release its own
        }
    }

    public void methodB() {
        lock.lock();              // reacquire the lock (reentrant)
        try {
            System.out.println("inner");
        } finally {
            lock.unlock();        // MUST unlock here to avoid imbalance
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ReentalLockExample reentalLockExample = new ReentalLockExample();
        reentalLockExample.methodA();
    }

    //🛠 When to Use lockInterruptibly()
    //Use it when:
    //Your thread may need to abort a long wait.
    //
    //You want to avoid getting stuck forever waiting on a lock.
    //
    //You're writing responsive, robust systems (e.g., timeout logic, cancellation, server shutdowns).


    //✅ When to Use
    //Use synchronized when:
    //
    //You need simple, reliable locking.
    //
    //You don’t need advanced features like tryLock or interruptible locks.
    //
    //You want less boilerplate code.
    //
    //Use ReentrantLock when:
    //
    //You need more control over the locking process.
    //
    //You need tryLock(), timeout, fairness, or multiple conditions.
    //
    //You want to be able to interrupt threads waiting on a lock.

    //| Feature                                  | `synchronized`                       | `ReentrantLock`                      |
    //| ---------------------------------------- | ------------------------------------ | ------------------------------------ |
    //| **Type**                                 | Keyword (language-level construct)   | Class (java.util.concurrent.locks)   |
    //| **Reentrancy**                           | Yes                                  | Yes                                  |
    //| **Fairness policy**                      | No                                   | Yes (optional constructor parameter) |
    //| **Interruptible lock acquisition**       | No                                   | Yes (`lockInterruptibly()`)          |
    //| **Try to acquire lock without blocking** | No                                   | Yes (`tryLock()`)                    |
    //| **Timeout support**                      | No                                   | Yes (`tryLock(timeout, unit)`)       |
    //| **Explicit unlock required?**            | No (auto-release)                    | Yes (must call `unlock()`)           |
    //| **Condition support (like wait/notify)** | Yes (`wait()/notify()`)              | Yes (`Condition` object)             |
    //| **Performance**                          | Slightly better for simple use cases | Slightly heavier due to flexibility  |
    //| **Debuggability**                        | Easier (syntax integrated)           | Harder (more code and complexity)    |

}
