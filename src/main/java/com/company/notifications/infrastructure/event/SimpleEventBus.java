package com.company.notifications.infrastructure.event;

import com.company.notifications.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleEventBus implements EventBus {

    private final List<Consumer<DomainEvent>> listeners = new ArrayList<>();

    public void register(Consumer<DomainEvent> listener) {
        listeners.add(listener);
    }

    @Override
    public void publish(DomainEvent event) {
        listeners.forEach(l -> l.accept(event));
    }
}
