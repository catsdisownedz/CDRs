// RandomDataGenerator Class
package org.example.utils;

import org.example.Main;
import org.example.database.entity.CDR;
import org.example.database.service.MultiThreader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.utils.StartDateTimeGenerator.formatter;


public class RandomDataGenerator {
    private final NameExtracter nameExtracter;
    private final ServiceTypeGenerator serviceTypeGenerator;
    private final UsageGenerator usageGenerator;
    private final StartDateTimeGenerator startDateTimeGenerator;
    public static final LocalDateTime time = LocalDateTime.now();
    public static List<String> callLogDates;
    private final List<String> names;
    private final List<String> usedNames;
    private final Random rd = new Random();
    private String lastDateUsed;

    public RandomDataGenerator(NameExtracter nameExtracter, ServiceTypeGenerator serviceTypeGenerator,
                               UsageGenerator usageGenerator, StartDateTimeGenerator startDateTimeGenerator) {
        this.nameExtracter = nameExtracter;
        this.serviceTypeGenerator = serviceTypeGenerator;
        this.usageGenerator = usageGenerator;
        this.startDateTimeGenerator = startDateTimeGenerator;
        this.names = nameExtracter.readNamesFromFile("data/names.csv", Main.NUM_RECORDS * 3);
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


    public String recordDate() {
        if (callLogDates.isEmpty()) {
            if (lastDateUsed != null) {
                return resumeFromLastDate();
            } else {
                throw new IllegalStateException("No more dates available for records");
            }
        }
        String date = callLogDates.get(0);
        callLogDates.remove(0);
        return date;
    }

    private String resumeFromLastDate() {
        if (lastDateUsed == null) {
            throw new IllegalStateException("No last date available to resume from");
        }
        // Generate new dates starting from the last date used
        System.out.println("Generating new dates");
        LocalDateTime lastDateTime = LocalDateTime.parse(lastDateUsed, formatter);
        List<String> newDates = StartDateTimeGenerator.randomStartDateTime(lastDateTime);
        callLogDates.addAll(newDates);
        // Return the first date from the newly generated dates
        String nextDate = callLogDates.get(0);
        callLogDates.remove(0);
        return nextDate;
    }

    // Generates a random CDR record
    public CDR generateRandomRecord() {
        String anum = personGenerator();
        String bnum = null;
        String serviceType = serviceTypeGenerator.randomServiceType();
        //System.out.println("generation started");
        if (!serviceType.equals("DATA")) {
            do {
                bnum = personGenerator();
            } while (bnum.equals(anum));
        }
       // System.out.println("call logs size: " + callLogDates.size());
        float usage = usageGenerator.activateRandomUsage(serviceType);
        String startDateTime = recordDate();
       // System.out.println("generation ended");
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
        System.out.println("call logs size: " + callLogDates.size());
        String startDateTime = recordDate();

        return new CDR(anum, bnum, serviceType, usage, startDateTime);
    }

}
