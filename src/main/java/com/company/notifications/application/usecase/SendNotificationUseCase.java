package com.company.notifications.application.usecase;

import com.company.notifications.application.pipeline.*;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.infrastructure.event.EventBus;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.retry.RetryPolicy;

import java.util.List;

public class SendNotificationUseCase {

    private final NotificationPipeline pipeline;

    public SendNotificationUseCase(ChannelRegistry registry, RetryPolicy retryPolicy, EventBus eventBus) {
        this.pipeline = new NotificationPipeline(List.of(
                new ValidateNotificationStep(),
                new ResolveChannelStep(registry),
                new ExecuteChannelStep(eventBus),
                new RetryStep(retryPolicy)
        ));
    }


    public NotificationResult execute(ChannelType type, Notification notification) {
        var context = new NotificationContext(type, notification);
        pipeline.execute(context);
        return context.getResult();
    }
}
