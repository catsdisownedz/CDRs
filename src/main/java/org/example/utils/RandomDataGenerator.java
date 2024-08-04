package org.example.utils;

import org.example.CDR;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {
    private final NameExtracter nameExtracter;
    private final ServiceTypeGenerator serviceTypeGenerator;
    private final UsageGenerator usageGenerator;
    private final StartDateTimeGenerator startDateTimeGenerator;
    private static final LocalDateTime time = LocalDateTime.now();
    private List<String> callLogDates;

    private final List<String> names;
    private final List<String> usedNames;
    private final Random rd = new Random();

    public RandomDataGenerator(NameExtracter nameExtracter, ServiceTypeGenerator serviceTypeGenerator, UsageGenerator usageGenerator, StartDateTimeGenerator startDateTimeGenerator) {
        this.nameExtracter = nameExtracter;
        this.serviceTypeGenerator = serviceTypeGenerator;
        this.usageGenerator = usageGenerator;
        this.startDateTimeGenerator = startDateTimeGenerator;
        this.names = nameExtracter.readNamesFromFile("data/names.csv", 10);
        this.usedNames = new ArrayList<>();
        this.callLogDates = startDateTimeGenerator.randomStartDateTime(time);
    }

    public String personGenerator() {
        String person;
        do {
            person = names.get(rd.nextInt(names.size()));
        } while (usedNames.contains(person));
        usedNames.add(person);
        System.out.println("Generated person: " + person);
        return person;
    }

    // Retrieves a date for the record and ensures no duplication
    public String recordDate() {
        if (callLogDates.isEmpty()) {
            throw new IllegalStateException("No more dates available for records");
        }
        String date = callLogDates.get(0);
        callLogDates.remove(0);
        System.out.println("Generated record date: " + date);
        return date;
    }

    // Generates a random CDR record
    public CDR generateRandomRecord() {
        System.out.println("Generating random record...");
        String anum = personGenerator();
        String bnum = null;
        String serviceType = serviceTypeGenerator.randomServiceType();

        if (!serviceType.equals("DATA")) {
            do {
                bnum = personGenerator();
            } while (bnum.equals(anum));
        }

        float usage = usageGenerator.activateRandomUsage(serviceType);
        String startDateTime = recordDate();

        System.out.println("Generated CDR: " + anum + ", " + bnum + ", " + serviceType + ", " + usage + ", " + startDateTime);
        return new CDR(anum, bnum, serviceType, usage, startDateTime);
    }
}
