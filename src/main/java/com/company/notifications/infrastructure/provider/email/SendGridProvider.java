package com.company.notifications.infrastructure.provider.email;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Provider;

public class SendGridProvider implements Provider {

    @Override
    public NotificationResult send(Notification notification) {
        System.out.println("Sending Email via SendGrid to " + notification.recipient());
        return NotificationResult.success("SendGrid");
    }
}
