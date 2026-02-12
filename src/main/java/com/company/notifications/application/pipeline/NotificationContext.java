package com.company.notifications.application.pipeline;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Channel;

public class NotificationContext {

    private final ChannelType channelType;
    private final Notification notification;
    private Channel channel;
    private NotificationResult result;

    public NotificationContext(ChannelType channelType, Notification notification) {
        this.channelType = channelType;
        this.notification = notification;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public Notification getNotification() {
        return notification;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public NotificationResult getResult() {
        return result;
    }

    public void setResult(NotificationResult result) {
        this.result = result;
    }
}
