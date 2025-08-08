package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    //non-blocking execution
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf1=CompletableFuture.supplyAsync(()->
        {
            try {
                Thread.sleep(1000);
                System.out.println("1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "DONE";
        });
        CompletableFuture<String> cf2=CompletableFuture.supplyAsync(()->
        {
            try {
                Thread.sleep(1000);
                System.out.println("2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "DONE";
        });
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(cf1, cf2);
        voidCompletableFuture.join();
//        System.out.println(cf1.get());//main thread will wait till the completion of future !!!Danger
        System.out.println("MAIN");

    }
}
//| Situation                                 | Blocking? |
//| ----------------------------------------- | --------- |
//| `supplyAsync(...)` only                   | ❌ No      |
//| `.get()` or `.join()` on the result       | ✅ Yes     |
//| Inside Spring controller without `.get()` | ❌ No      |
//| Using `.thenApply()`, `.thenAccept()` etc | ❌ No      |