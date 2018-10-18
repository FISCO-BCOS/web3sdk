package org.bcos.web3j.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Async task facilitation.
 */
public class Async {
    static Logger logger = LoggerFactory.getLogger(Async.class);
    private static Executor executor = Executors.newFixedThreadPool(Web3AsyncThreadPoolSize.web3AsyncPoolSize);
    ;
    public Async(ThreadPoolTaskExecutor pool){
            logger.info("Async:ThreadPoolTaskExecutor getCorePoolSize " + pool.getCorePoolSize() + " getMaxPoolSize " + pool.getMaxPoolSize());
            Async.executor = pool;
    }
     public Async( ){
     }
    public static <T> CompletableFuture<T> run(Callable<T> callable) {
        CompletableFuture<T> result = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            // we need to explicityly catch any exceptions,
            // otherwise they will be silently discarded
            try {
                result.complete(callable.call());
            } catch (Throwable e) {
                result.completeExceptionally(e);
            }
        }, Async.executor);
        return result;
    }

    private static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static ScheduledExecutorService defaultExecutorService() {
        return Executors.newScheduledThreadPool(getCpuCount());
    }
}
