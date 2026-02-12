package com.company.notifications.infrastructure.resilience;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {

    private final int failureThreshold;
    private final long resetTimeoutMs;

    private final AtomicInteger failures = new AtomicInteger(0);
    private volatile Instant lastFailureTime = Instant.MIN;
    private volatile boolean open = false;

    public CircuitBreaker(int failureThreshold, long resetTimeoutMs) {
        this.failureThreshold = failureThreshold;
        this.resetTimeoutMs = resetTimeoutMs;
    }

    public synchronized boolean allowRequest() {
        if (!open) return true;

        if (Instant.now().toEpochMilli() - lastFailureTime.toEpochMilli() > resetTimeoutMs) {
            open = false;
            failures.set(0);
            return true;
        }
        return false;
    }

    public synchronized void recordSuccess() {
        failures.set(0);
        open = false;
    }

    public synchronized void recordFailure() {
        int count = failures.incrementAndGet();
        lastFailureTime = Instant.now();
        if (count >= failureThreshold) {
            open = true;
        }
    }
}
