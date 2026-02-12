package com.company.notifications.application.pipeline;

import com.company.notifications.domain.model.NotificationResult;

public class ExecuteChannelStep implements PipelineStep {

    @Override
    public void execute(NotificationContext context) {
        if (context.getResult() != null) {
            return; // ya falló en validación
        }

        var channel = context.getChannel();
        NotificationResult result = channel.send(context.getNotification());
        context.setResult(result);
    }
}
