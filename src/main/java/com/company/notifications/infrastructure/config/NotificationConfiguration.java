package com.company.notifications.infrastructure.config;

import com.company.notifications.api.NotificationClient;
import com.company.notifications.application.usecase.SendNotificationUseCase;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.infrastructure.channel.EmailChannel;
import com.company.notifications.infrastructure.channel.PushChannel;
import com.company.notifications.infrastructure.channel.SmsChannel;
import com.company.notifications.infrastructure.event.SimpleEventBus;
import com.company.notifications.infrastructure.event.listener.LoggingEventListener;
import com.company.notifications.infrastructure.provider.ResilientProvider;
import com.company.notifications.infrastructure.provider.ThrottledProvider;
import com.company.notifications.infrastructure.provider.email.SendGridProvider;
import com.company.notifications.infrastructure.provider.push.FirebaseProvider;
import com.company.notifications.infrastructure.provider.sms.TwilioProvider;
import com.company.notifications.infrastructure.resilience.CircuitBreaker;
import com.company.notifications.infrastructure.resilience.RateLimiter;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.SimpleRetryPolicy;
import java.util.List;
import com.company.notifications.infrastructure.provider.email.FailingEmailProvider;

public class NotificationConfiguration {

    public static NotificationClient defaultClient() {

        var registry = new ChannelRegistry();
        var eventBus = new SimpleEventBus();
        var logger = new LoggingEventListener();

        var circuitBreaker = new CircuitBreaker(3, 5000);
        var rateLimiter = new RateLimiter(5, 1000);

        var emailProvider =
                new ThrottledProvider(
                        new ResilientProvider(
                                new SendGridProvider(),
                                circuitBreaker
                        ),
                        rateLimiter
                );

        registry.register(
                ChannelType.EMAIL,
                new EmailChannel(List.of(
                        new FailingEmailProvider(),
                        new SendGridProvider()
                ))
        );

// Activar el retry
//        registry.register(
//                ChannelType.EMAIL,
//                new EmailChannel(List.of(new FailingEmailProvider()))
//        );




        eventBus.register(logger::handle);

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
