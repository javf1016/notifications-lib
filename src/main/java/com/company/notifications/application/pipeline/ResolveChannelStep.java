package com.company.notifications.application.pipeline;

import com.company.notifications.resolver.ChannelRegistry;

public class ResolveChannelStep implements PipelineStep {

    private final ChannelRegistry registry;

    public ResolveChannelStep(ChannelRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void execute(NotificationContext context) {
        var channel = registry.resolve(context.getChannelType());
        context.setChannel(channel);
    }
}
