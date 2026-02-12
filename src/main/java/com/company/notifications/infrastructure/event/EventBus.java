package com.company.notifications.infrastructure.event;

import com.company.notifications.domain.event.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event);
}
