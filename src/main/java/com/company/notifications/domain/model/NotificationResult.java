package com.company.notifications.domain.model;

public record NotificationResult(
        NotificationStatus status,
        String provider,
        String errorMessage
) {
    public static NotificationResult success(String provider) {
        return new NotificationResult(NotificationStatus.SUCCESS, provider, null);
    }

    public static NotificationResult failure(String error) {
        return new NotificationResult(NotificationStatus.FAILED, null, error);
    }

    public boolean isSuccess() {
        return status == NotificationStatus.SUCCESS;
    }
}
