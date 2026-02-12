package com.company.notifications.application.pipeline;

import com.company.notifications.retry.RetryPolicy;

public class RetryStep implements PipelineStep {

    private final RetryPolicy retryPolicy;

    public RetryStep(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    @Override
    public void execute(NotificationContext context) {
        if (context.getResult() != null && context.getResult().isSuccess()) {
            return;
        }

        var channel = context.getChannel();

        var result = retryPolicy.execute(() ->
                channel.send(context.getNotification())
        );

        context.setResult(result);
    }
}
