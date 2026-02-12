package com.company.notifications.infrastructure.provider.email;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Provider;

public class FailingEmailProvider implements Provider {

    @Override
    public NotificationResult send(Notification notification) {
        System.out.println("Failing Email Provider");
        return NotificationResult.failure("Provider down");
    }
}
