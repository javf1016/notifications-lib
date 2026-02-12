package com.company.notifications.infrastructure.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationExecutor {

    private final ExecutorService executorService;

    public NotificationExecutor(int threads) {
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    public void execute(Runnable task) {
        executorService.submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
