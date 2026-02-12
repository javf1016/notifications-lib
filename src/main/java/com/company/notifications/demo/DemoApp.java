package com.company.notifications.demo;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.infrastructure.config.NotificationConfiguration;

public class DemoApp {

    public static void main(String[] args) {

        var client = NotificationConfiguration.defaultClient();

        var notification = new Notification(
                "jorge@test.com",
                "Hola",
                "Mensaje Jorge Andres Vera Forero"
        );

        var result = client.send(ChannelType.EMAIL, notification);

        System.out.println(result);
    }
}
