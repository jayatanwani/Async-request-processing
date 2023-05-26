package org.example.utils;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExecutors {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutors.class);

    public ThreadPoolExecutors() {
    }

    public static ThreadPoolExecutor createEventExecutor(String resourceName,
                                                         Thread.UncaughtExceptionHandler handler, int poolSize, int queueSize) {

        LOGGER.info("Constructing Thread Pool with Custom Discard Oldest Policy for Resource {} with QueueSize {} " +
                "Pool Size {}", resourceName, queueSize, poolSize);

        return create(resourceName, handler, queueSize, poolSize);
    }

    private static ThreadPoolExecutor create(String resourceName,
                                             Thread.UncaughtExceptionHandler handler, int queueSize, int poolSize) {

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(resourceName + "_processor-thread-%d")
                .setUncaughtExceptionHandler(handler)
                .build();

        final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueSize);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize,
                poolSize,
                0L,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory);

        return executor;
    }
}

