package org.example;

public class ThreadCommunication {

    //| Method        | Description                  | Must be in `synchronized` block? | Affects which threads?     |
    //| ------------- | ---------------------------- | -------------------------------- | -------------------------- |
    //| `wait()`      | Pauses thread, releases lock | ✅ Yes                            | The current thread         |
    //| `notify()`    | Wakes one waiting thread     | ✅ Yes                            | One thread on same object  |
    //| `notifyAll()` | Wakes all waiting threads    | ✅ Yes                            | All threads on same object |

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread p = new Producer(sharedResource);
        Thread c = new Consumer(sharedResource);
        c.start();
        p.start();

    }
}

class Producer extends Thread {
    private final SharedResource sharedResource;

    Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedResource.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer extends Thread {
    private final SharedResource sharedResource;

    Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedResource.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int i) throws InterruptedException {
        while (hasData) {
            wait();//must be used in sync block
            System.out.println("producer waiting");
        }
        hasData = true;
        data = i;
        System.out.println("Produced " + i);
        notify();//must be used in sync block
        //notify the object from which it is used
    }

    public synchronized void consume() throws InterruptedException {
        while (!hasData){
            wait();
            System.out.println("consumer waiting");
        }
        hasData = false;
        notify();//notify the sharedResource object
        System.out.println("Consumed " + data);
    }
}
