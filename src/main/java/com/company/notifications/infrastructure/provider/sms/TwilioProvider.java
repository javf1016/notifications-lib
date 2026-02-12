package com.company.notifications.infrastructure.provider.sms;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Provider;

public class TwilioProvider implements Provider {

    @Override
    public NotificationResult send(Notification notification) {
        System.out.println("Sending SMS via Twilio to " + notification.recipient());
        return NotificationResult.success("Twilio");
    }
}
