package com.company.notifications.infrastructure.channel;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Channel;
import com.company.notifications.ports.out.Provider;

import java.util.List;

public class SmsChannel implements Channel {

    private final List<Provider> providers;

    public SmsChannel(List<Provider> providers) {
        this.providers = providers;
    }

    @Override
    public NotificationResult send(Notification notification) {
        for (Provider provider : providers) {
            var result = provider.send(notification);
            if (result.isSuccess()) {
                return result;
            }
        }
        return NotificationResult.failure("All SMS providers failed");
    }
}
