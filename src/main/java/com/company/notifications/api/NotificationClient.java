package com.company.notifications.api;

import com.company.notifications.application.usecase.SendNotificationUseCase;
import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.domain.model.NotificationResult;

public class NotificationClient {

    private final SendNotificationUseCase useCase;

    public NotificationClient(SendNotificationUseCase useCase) {
        this.useCase = useCase;
    }

    public NotificationResult send(ChannelType type, Notification notification) {
        return useCase.execute(type, notification);
    }
}
