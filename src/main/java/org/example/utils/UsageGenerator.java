package org.example.utils;
import org.example.CDR;
import java.util.Random;

public class UsageGenerator {
    public static float activateRandomUsage(String serviceType) {
        Random rd = new Random();
        return switch (serviceType) {
            case "CALL" -> Math.round(((rd.nextFloat() * 60)*1000.0f)/1000.0f); // up to 60 minutes
            case "DATA" -> Math.round(((rd.nextFloat() * 1000)*1000.0f)/1000.0f); // up to 1000 MB
            case "SMS" -> 1;
            default -> throw new IllegalArgumentException("Unknown service type: " + serviceType);
        };
    }





}
