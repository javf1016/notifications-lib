package com.company.notifications.application.usecase;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.RetryPolicy;

public class SendNotificationUseCase {

    private final ChannelRegistry registry;
    private final RetryPolicy retryPolicy;

    public SendNotificationUseCase(ChannelRegistry registry, RetryPolicy retryPolicy) {
        this.registry = registry;
        this.retryPolicy = retryPolicy;
    }

    public NotificationResult execute(ChannelType type, Notification notification) {
        var channel = registry.resolve(type);
        return retryPolicy.execute(() -> channel.send(notification));
    }
}
