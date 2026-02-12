package com.company.notifications.tests;

import com.company.notifications.application.usecase.SendNotificationUseCase;
import com.company.notifications.domain.event.DomainEvent;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.domain.model.NotificationStatus;
import com.company.notifications.infrastructure.event.EventBus;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.RetryPolicy;
import com.company.notifications.retry.SimpleRetryPolicy;
import com.company.notifications.ports.out.Channel;

public class SendNotificationUseCaseTest {

    public static void run() {
        System.out.println("[TEST] SendNotificationUseCase");

        // 1) Fake Channel
        Channel fakeEmailChannel = new Channel() {
            @Override
            public NotificationResult send(Notification notification) {
                return new NotificationResult(
                        NotificationStatus.SUCCESS,
                        "FakeEmailProvider",
                        null
                );
            }
        };

        // 2) ChannelRegistry real
        ChannelRegistry registry = new ChannelRegistry();
        registry.register(ChannelType.EMAIL, fakeEmailChannel);

        // 3) RetryPolicy real
        RetryPolicy retryPolicy = new SimpleRetryPolicy(1);

        // 4) Fake EventBus (correct signature)
        EventBus eventBus = new EventBus() {
            @Override
            public void publish(DomainEvent event) {
                System.out.println("[EVENT BUS TEST] " + event.getClass().getSimpleName());
            }
        };

        // 5) UseCase real
        SendNotificationUseCase useCase =
                new SendNotificationUseCase(registry, retryPolicy, eventBus);

        Notification notification = new Notification(
                "test@test.com",
                "Hello",
                "Message"
        );

        NotificationResult result = useCase.execute(ChannelType.EMAIL, notification);

        if (!result.isSuccess()) {
            throw new RuntimeException("❌ UseCase test failed: expected SUCCESS");
        }

        if (!"FakeEmailProvider".equals(result.provider())) {
            throw new RuntimeException("❌ UseCase test failed: wrong provider");
        }

        System.out.println("OK");
    }
}
