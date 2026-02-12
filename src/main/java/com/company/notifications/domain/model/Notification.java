package com.company.notifications.domain.model;

public record Notification(
        String recipient,
        String subject,
        String message
) {}