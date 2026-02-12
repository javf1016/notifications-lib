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
            count++;

            System.out.println("Retry attempt " + count);

            result = action.get();

            if (result.isSuccess()) {
                System.out.println("Retry success on attempt " + count);
                return result;
            } else {
                System.out.println("Retry failed on attempt " + count);
            }

        } while (count < attempts);

        System.out.println("All retry attempts failed");
        return result;
    }
}
