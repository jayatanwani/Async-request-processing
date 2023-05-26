package org.example.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class AbstractTaskHandler {

    public AbstractTaskHandler(){
    // default constructor
    }

    public CompletableFuture<Void> handleTask(Runnable runnable, ExecutorService executorService){
        return CompletableFuture.runAsync(runnable, executorService);
    }

}
