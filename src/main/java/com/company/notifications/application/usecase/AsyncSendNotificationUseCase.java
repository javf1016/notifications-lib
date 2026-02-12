package com.company.notifications.application.usecase;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.infrastructure.async.NotificationExecutor;

public class AsyncSendNotificationUseCase {

    private final SendNotificationUseCase syncUseCase;
    private final NotificationExecutor executor;

    public AsyncSendNotificationUseCase(SendNotificationUseCase syncUseCase,
                                        NotificationExecutor executor) {
        this.syncUseCase = syncUseCase;
        this.executor = executor;
    }

    public void execute(ChannelType type, Notification notification) {
        executor.execute(() -> syncUseCase.execute(type, notification));
    }
}
