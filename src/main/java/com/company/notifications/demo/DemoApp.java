package com.company.notifications.demo;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.domain.model.Notification;
import com.company.notifications.infrastructure.config.NotificationConfiguration;

public class DemoApp {

    public static void main(String[] args) {

        var client = NotificationConfiguration.defaultClient();

        var notification = new Notification(
                "jorge@test.com",
                "Hello",
                "Message Jorge Andres Vera Forero"
        );

        var resultMail = client.send(ChannelType.EMAIL, notification);

        var resultSms = client.send(
                ChannelType.SMS,
                new Notification("3187949964", "SMS", "Hello Test SMS")
        );

        var resultPush = client.send(
                ChannelType.PUSH,
                new Notification("device-id-123", "Push", "Hello Test Push")
        );


        System.out.println(resultMail);
        System.out.println(resultSms);
        System.out.println(resultPush);
    }
}
