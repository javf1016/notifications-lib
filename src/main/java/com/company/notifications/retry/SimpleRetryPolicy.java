package com.company.notifications.retry;

import com.company.notifications.domain.model.NotificationResult;

import java.util.function.Supplier;

public class SimpleRetryPolicy implements RetryPolicy {

    private final int attempts;

    public SimpleRetryPolicy(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public NotificationResult execute(Supplier<NotificationResult> action) {
        NotificationResult result;
        int count = 0;

        do {
            result = action.get();
            count++;
        } while (!result.isSuccess() && count < attempts);

        return result;
    }
}
