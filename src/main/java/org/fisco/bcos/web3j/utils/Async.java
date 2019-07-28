package org.fisco.bcos.web3j.utils;

import static org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize.web3AsyncPoolSize;

import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Async task facilitation. */
public class Async {

    static Logger logger = LoggerFactory.getLogger(Async.class);

    private static Executor executor;

    public static <T> CompletableFuture<T> run(Callable<T> callable) {

        if (null == executor) {
            logger.info(" default set setExeutor , pool size is {}", web3AsyncPoolSize);
            setExeutor(Executors.newFixedThreadPool(web3AsyncPoolSize), true);
        }

        CompletableFuture<T> result = new CompletableFuture<>();
        CompletableFuture.runAsync(
                () -> {
                    // we need to explicitly catch any exceptions,
                    // otherwise they will be silently discarded
                    try {
                        result.complete(callable.call());
                    } catch (Throwable e) {
                        result.completeExceptionally(e);
                    }
                },
                executor);
        return result;
    }

    private static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Provide a new ScheduledExecutorService instance.
     *
     * <p>A shutdown hook is created to terminate the thread pool on application termination.
     *
     * @return new ScheduledExecutorService
     */
    public static ScheduledExecutorService defaultExecutorService() {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(getCpuCount());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scheduledExecutorService)));

        return scheduledExecutorService;
    }

    /**
     * Shutdown as per {@link ExecutorService} Javadoc recommendation.
     *
     * @param executorService executor service we wish to shut down.
     */
    private static void shutdown(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Thread pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static synchronized void setExeutor(Executor pool, boolean setIfNull) {
        if (null == Async.executor && setIfNull) {
            Async.executor = pool;
            logger.info(" set setExeutor because executor null, executor is {}", pool.toString());
        } else if (!setIfNull) {
            Async.executor = pool;
            logger.info(
                    " set setExeutor even executor already exist, executor is {}", pool.toString());
        }
    }

    public Async(Executor pool) {
        setExeutor(pool, true);
    }
}
