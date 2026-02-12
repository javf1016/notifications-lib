package com.company.notifications.infrastructure.channel;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Channel;
import com.company.notifications.ports.out.Provider;

public class PushChannel implements Channel {

    private final Provider provider;

    public PushChannel(Provider provider) {
        this.provider = provider;
    }

    @Override
    public NotificationResult send(Notification notification) {
        return provider.send(notification);
    }
}
