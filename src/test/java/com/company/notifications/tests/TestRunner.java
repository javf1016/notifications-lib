package com.company.notifications.tests;

public class TestRunner {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" RUNNING NOTIFICATION LIB TESTS ");
        System.out.println("=================================");

        SendNotificationUseCaseTest.run();
        RetryPolicyTest.run();
        ChannelRegistryTest.run();

        System.out.println("=================================");
        System.out.println(" ALL TESTS PASSED SUCCESSFULLY ");
        System.out.println("=================================");
    }
}
