package com.company.notifications.infrastructure.config;

import com.company.notifications.api.NotificationClient;
import com.company.notifications.application.usecase.SendNotificationUseCase;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.infrastructure.channel.EmailChannel;
import com.company.notifications.infrastructure.provider.email.SendGridProvider;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.SimpleRetryPolicy;

public class NotificationConfiguration {

    public static NotificationClient defaultClient() {

        var registry = new ChannelRegistry();

        registry.register(
                ChannelType.EMAIL,
                new EmailChannel(new SendGridProvider())
        );

        var retryPolicy = new SimpleRetryPolicy(3);

        var useCase = new SendNotificationUseCase(registry, retryPolicy);

        return new NotificationClient(useCase);
    }
}
