package com.company.notifications.infrastructure.provider.push;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;
import com.company.notifications.ports.out.Provider;

public class FirebaseProvider implements Provider {

    @Override
    public NotificationResult send(Notification notification) {
        System.out.println("Sending Push Firebase to " + notification.recipient());
        return NotificationResult.success("Firebase");
    }
}
