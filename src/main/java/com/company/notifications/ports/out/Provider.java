package com.company.notifications.ports.out;

import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;

public interface Provider {
    NotificationResult send(Notification notification);
}
