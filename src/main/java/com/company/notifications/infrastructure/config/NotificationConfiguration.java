package com.company.notifications.infrastructure.config;

import com.company.notifications.api.NotificationClient;
import com.company.notifications.application.usecase.SendNotificationUseCase;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.infrastructure.channel.EmailChannel;
import com.company.notifications.infrastructure.channel.PushChannel;
import com.company.notifications.infrastructure.channel.SmsChannel;
import com.company.notifications.infrastructure.event.SimpleEventBus;
import com.company.notifications.infrastructure.event.listener.LoggingEventListener;
import com.company.notifications.infrastructure.provider.email.SendGridProvider;
import com.company.notifications.infrastructure.provider.push.FirebaseProvider;
import com.company.notifications.infrastructure.provider.sms.TwilioProvider;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.SimpleRetryPolicy;
import java.util.List;
import com.company.notifications.infrastructure.provider.email.FailingEmailProvider;

public class NotificationConfiguration {

    public static NotificationClient defaultClient() {

        var registry = new ChannelRegistry();
        var eventBus = new SimpleEventBus();
        var logger = new LoggingEventListener();
        eventBus.register(logger::handle);


        registry.register(
                ChannelType.EMAIL,
                new EmailChannel(List.of(
                        new FailingEmailProvider(),
                        new SendGridProvider()
                ))
        );


        registry.register(
                ChannelType.SMS,
                new SmsChannel(List.of(new TwilioProvider()))
        );

        registry.register(
                ChannelType.PUSH,
                new PushChannel(List.of(new FirebaseProvider()))
        );

        var retryPolicy = new SimpleRetryPolicy(3);

        return new NotificationClient(
                new SendNotificationUseCase(registry, retryPolicy, eventBus)
        );

    }
}
