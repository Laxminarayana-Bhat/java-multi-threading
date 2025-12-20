package org.example;

public class ThreadLifeCycle extends Thread {
    public ThreadLifeCycle(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("RUNNING");
        try {
            Thread.sleep(1000);//sleep
            System.out.println(Thread.currentThread().getPriority() + "--" + Thread.currentThread().getName());
            Thread.yield();//give chance to other thread to run
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new ThreadLifeCycle("default");
        System.out.println(thread.getState());//NEW
        thread.start();
        System.out.println(thread.getState());//RUNNABLE
        //practically nothing as RUNNING state in java, jvm initiates when cpu is available for task execution
        Thread.sleep(100);//main thread will sleep
        System.out.println(thread.getState());//TIMED WAITING
        thread.join();
        System.out.println(thread.getState());//TERMINATED


        //Priority
        //1-min 5-normal 10-high
        Thread l = new ThreadLifeCycle("LOW");
        l.setPriority(Thread.MIN_PRIORITY);
        Thread m = new ThreadLifeCycle("Medium");
        m.setPriority(Thread.NORM_PRIORITY);
        Thread h = new ThreadLifeCycle("HIGH");
        h.setPriority(Thread.MAX_PRIORITY);
        h.start();//1st
        l.start();//3rd
        m.start();//2nd
        m.interrupt();//invoke stop to the thread, in whatever the state it may be
        m.join();
        l.join();
        h.join();

        Thread thread1=new YieldThread("thread1");
        Thread thread2=new YieldThread("thread2");
        thread1.start();
        thread2.start();//for yield

        //Daemon threads - Background threads
        Thread daemon=new DaemonThreadEx("daemon");
        daemon.setDaemon(true);
        daemon.start();
        System.out.println("Main done "+Thread.currentThread().getName());
        //here it should have ran infinite loop
        //but as it's set as daemon, it will stop after all the work of main are done

    }

    static class YieldThread extends Thread{
        public YieldThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                System.out.println(i+" no, Thread running:"+Thread.currentThread().getName());
                Thread.yield();//give time to other threads also
            }
        }
    }

    static class DaemonThreadEx extends Thread{
        public DaemonThreadEx(String name) {
            super(name);
        }

        @Override
        public void run() {
            for(;;){
                System.out.println("Keep Running");
            }
        }
    }
}
