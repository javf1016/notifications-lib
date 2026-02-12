package com.company.notifications.application.pipeline;

import java.util.List;

public class NotificationPipeline {

    private final List<PipelineStep> steps;

    public NotificationPipeline(List<PipelineStep> steps) {
        this.steps = steps;
    }

    public void execute(NotificationContext context) {
        for (PipelineStep step : steps) {
            step.execute(context);
        }
    }
}
