package org.example.phase2;

import java.sql.Time;
import java.util.concurrent.*;

public class ExecutorEx {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executors= Executors.newFixedThreadPool(10);
        Integer object=2;
        Future<Integer> submit = executors.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                return 0;
            }
        });
        Integer i = submit.get();
        //This is not good as it uses LinkedBlockingQueue<>()

        //Use this for prod or important operations as here we can add array blocking queue
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(3,4, 100,TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> 10)
                .thenApply(x -> x * x)
                .thenAccept(System.out::println);//with return value

        voidCompletableFuture.get();//Now its blocking
        CompletableFuture.runAsync(()-> System.out.println("Running"));//void
//        while(true){
//            Thread.sleep(1000);
//            CompletableFuture.runAsync(()-> System.out.println("Running"));
//        }

        CompletableFuture<Integer> job1=CompletableFuture.supplyAsync(()->20);
        CompletableFuture<Integer> job2=CompletableFuture.supplyAsync(()->230);
        CompletableFuture<Integer> job3=CompletableFuture.supplyAsync(()->201);
        CompletableFuture<Integer> completableFuture = job1.thenCombine(job2, Integer::sum).thenCombine(job3, Integer::sum);
        completableFuture.thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException();
            return 10;
        }).whenComplete((res,ex)->{//takes function and exception
            if (ex!=null){
                System.out.println("exception");
            }else {
                System.out.println("NO ex");
            }
        }).exceptionally(ex -> -1); // fallback

        //with executor
        Executor executor = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> 10, executor)
                .thenAccept(System.out::println);
    }
}
