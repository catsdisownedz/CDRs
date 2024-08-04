package org.example.utils;

import java.util.Random;

public class ServiceTypeGenerator {
    private static final Random rd = new Random();
    private static final String[] services = {"CALL", "SMS", "DATA"};

    public static String randomServiceType() {
        return services[rd.nextInt(services.length)];
    }
}
