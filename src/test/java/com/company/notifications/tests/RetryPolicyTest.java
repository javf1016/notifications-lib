package com.company.notifications.tests;

import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.domain.model.NotificationStatus;
import com.company.notifications.retry.SimpleRetryPolicy;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryPolicyTest {

    public static void run() {
        System.out.println("[TEST] SimpleRetryPolicy");

        AtomicInteger attempts = new AtomicInteger(0);

        NotificationResult result = getNotificationResult(attempts);

        if (!result.isSuccess()) {
            throw new RuntimeException("❌ Retry test failed: should succeed");
        }

        if (attempts.get() != 2) {
            throw new RuntimeException("❌ Retry test failed: wrong attempts count");
        }

        System.out.println("OK");
    }

    private static NotificationResult getNotificationResult(AtomicInteger attempts) {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3);

        return retryPolicy.execute(() -> {
            int count = attempts.incrementAndGet();

            if (count < 2) {
                return new NotificationResult(
                        NotificationStatus.FAILED,
                        "FailingProvider",
                        "Error"
                );
            }

            return new NotificationResult(
                    NotificationStatus.SUCCESS,
                    "SuccessProvider",
                    null
            );
        });
    }
}
