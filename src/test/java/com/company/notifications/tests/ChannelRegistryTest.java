package com.company.notifications.tests;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.resolver.ChannelRegistry;
import com.company.notifications.ports.out.Channel;

public class ChannelRegistryTest {

    public static void run() {
        System.out.println("[TEST] ChannelRegistry");

        Channel emailChannel = new Channel() {
            @Override
            public com.company.notifications.domain.model.NotificationResult send(
                    com.company.notifications.domain.model.Notification notification) {
                return null;
            }
        };

        ChannelRegistry registry = new ChannelRegistry();
        registry.register(ChannelType.EMAIL, emailChannel);

        if (registry.resolve(ChannelType.EMAIL) == null) {
            throw new RuntimeException("Registry test failed: EMAIL not found");
        }

        if (registry.resolve(ChannelType.SMS) != null) {
            throw new RuntimeException("Registry test failed: SMS should not exist");
        }

        System.out.println("OK");
    }
}
