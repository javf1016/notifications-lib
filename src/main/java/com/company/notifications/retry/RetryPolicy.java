package com.company.notifications.retry;

import com.company.notifications.domain.model.NotificationResult;

import java.util.function.Supplier;

public interface RetryPolicy {
    NotificationResult execute(Supplier<NotificationResult> action);
}
