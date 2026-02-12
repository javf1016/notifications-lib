package com.company.notifications.application.pipeline;

import com.company.notifications.domain.event.NotificationFailedEvent;
import com.company.notifications.domain.event.NotificationSentEvent;
import com.company.notifications.infrastructure.event.EventBus;

public class ExecuteChannelStep implements PipelineStep {

    private final EventBus eventBus;

    public ExecuteChannelStep(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute(NotificationContext context) {
        if (context.getResult() != null) {
            return;
        }

        var channel = context.getChannel();
        var result = channel.send(context.getNotification());
        context.setResult(result);

        if (result.isSuccess()) {
            eventBus.publish(new NotificationSentEvent(result));
        } else {
            eventBus.publish(new NotificationFailedEvent(result));
        }
    }
}
