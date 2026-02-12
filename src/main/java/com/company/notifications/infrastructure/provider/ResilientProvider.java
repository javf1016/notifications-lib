package com.company.notifications.infrastructure.provider;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.infrastructure.resilience.CircuitBreaker;
import com.company.notifications.ports.out.Provider;

public class ResilientProvider implements Provider {

    private final Provider delegate;
    private final CircuitBreaker circuitBreaker;

    public ResilientProvider(Provider delegate, CircuitBreaker circuitBreaker) {
        this.delegate = delegate;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public NotificationResult send(Notification notification) {
        if (!circuitBreaker.allowRequest()) {
            return NotificationResult.failure("Circuit breaker open");
        }

        try {
            var result = delegate.send(notification);
            if (result.isSuccess()) {
                circuitBreaker.recordSuccess();
            } else {
                circuitBreaker.recordFailure();
            }
            return result;
        } catch (Exception e) {
            circuitBreaker.recordFailure();
            return NotificationResult.failure(e.getMessage());
        }
    }
}
