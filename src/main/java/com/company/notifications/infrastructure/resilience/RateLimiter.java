package com.company.notifications.infrastructure.resilience;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {

    private final int maxRequests;
    private final long windowMs;

    private AtomicInteger counter = new AtomicInteger(0);
    private volatile long windowStart = Instant.now().toEpochMilli();

    public RateLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
    }

    public synchronized boolean allowRequest() {
        long now = Instant.now().toEpochMilli();
        if (now - windowStart > windowMs) {
            windowStart = now;
            counter.set(0);
        }
        return counter.incrementAndGet() <= maxRequests;
    }
}
