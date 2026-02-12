package com.company.notifications.infrastructure.event.listener;

import com.company.notifications.domain.event.DomainEvent;

public class LoggingEventListener {

    public void handle(DomainEvent event) {
        System.out.println("EVENT -> " + event.getClass().getSimpleName());
    }
}
