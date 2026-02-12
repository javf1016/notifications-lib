package com.company.notifications.infrastructure.provider;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.infrastructure.resilience.RateLimiter;
import com.company.notifications.ports.out.Provider;

public class ThrottledProvider implements Provider {

    private final Provider delegate;
    private final RateLimiter rateLimiter;

    public ThrottledProvider(Provider delegate, RateLimiter rateLimiter) {
        this.delegate = delegate;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public NotificationResult send(Notification notification) {
        if (!rateLimiter.allowRequest()) {
            return NotificationResult.failure("Rate limit exceeded");
        }
        return delegate.send(notification);
    }
}
