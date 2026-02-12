package com.company.notifications.domain.event;

import com.company.notifications.domain.model.NotificationResult;

import java.time.Instant;

public record NotificationSentEvent(NotificationResult result) implements DomainEvent {

    @Override
    public Instant occurredAt() {
        return Instant.now();
    }
}
