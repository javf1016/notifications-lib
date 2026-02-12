package com.company.notifications.application.pipeline;

public interface PipelineStep {
    void execute(NotificationContext context);
}
