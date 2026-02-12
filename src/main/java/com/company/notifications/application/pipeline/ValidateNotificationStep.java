package com.company.notifications.application.pipeline;

import com.company.notifications.domain.model.NotificationResult;

public class ValidateNotificationStep implements PipelineStep {

    @Override
    public void execute(NotificationContext context) {
        var n = context.getNotification();

        if (n.recipient() == null || n.recipient().isBlank()) {
            context.setResult(NotificationResult.failure("Recipient is required"));
        }

        if (n.message() == null || n.message().isBlank()) {
            context.setResult(NotificationResult.failure("Message is required"));
        }
    }
}
