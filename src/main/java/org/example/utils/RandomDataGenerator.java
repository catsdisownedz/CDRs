// RandomDataGenerator Class
package org.example.utils;

import org.example.Main;
import org.example.database.entity.CDR;
import org.example.database.service.MultiThreader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {
    private final NameExtracter nameExtracter;
    private final ServiceTypeGenerator serviceTypeGenerator;
    private final UsageGenerator usageGenerator;
    private final StartDateTimeGenerator startDateTimeGenerator;
    public static final LocalDateTime time = LocalDateTime.now();
    public static List<String> callLogDates;
   // private final MultiThreader multi;
    private final List<String> names;
    private final List<String> usedNames;
    private final Random rd = new Random();
   // public static int numNames = rd.nextInt(500)+200;
    public RandomDataGenerator(NameExtracter nameExtracter, ServiceTypeGenerator serviceTypeGenerator, UsageGenerator usageGenerator, StartDateTimeGenerator startDateTimeGenerator) {
        this.nameExtracter = nameExtracter;
        this.serviceTypeGenerator = serviceTypeGenerator;
        this.usageGenerator = usageGenerator;
        this.startDateTimeGenerator = startDateTimeGenerator;
        this.names = nameExtracter.readNamesFromFile("data/names.csv", Main.NUM_RECORDS);
        this.usedNames = new ArrayList<>();
        this.callLogDates = startDateTimeGenerator.randomStartDateTime(time);
    }

    public String personGenerator() {
        String person;
        do {
            person = names.get(rd.nextInt(names.size()));
        } while (usedNames.contains(person));
        usedNames.add(person);
        return person;
    }

    // retrieves a date for the record and ensures no duplication
    public String recordDate() {
        if (callLogDates.isEmpty()) {
            throw new IllegalStateException("No more dates available for records");
        }
        String date = callLogDates.get(0);
        callLogDates.remove(0);
        //System.out.println("Generated record date: " + date);
        return date;
    }

    // Generates a random CDR record
    public CDR generateRandomRecord() {
        //System.out.println("Generating random record...");
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

        return new CDR(anum, bnum, serviceType, usage, startDateTime);
    }

    public CDR generateRecordsForDate(LocalDateTime specificDate) {
        String anum = personGenerator();
        String bnum = null;
        String serviceType = serviceTypeGenerator.randomServiceType();

        if (!serviceType.equals("DATA")) {
            do {
                bnum = personGenerator();
            } while (bnum.equals(anum));
        }

        float usage = usageGenerator.activateRandomUsage(serviceType);
        callLogDates = startDateTimeGenerator.randomStartDateTime(specificDate);
        String startDateTime = recordDate();

        return new CDR(anum, bnum, serviceType, usage, startDateTime);
    }

}
