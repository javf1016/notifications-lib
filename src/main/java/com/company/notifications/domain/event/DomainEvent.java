package com.company.notifications.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}
