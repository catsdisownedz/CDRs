package org.example.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartDateTimeGenerator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Random rd = new Random();

    public static List<String> randomStartDateTime(LocalDateTime now) {
        List <String> dates =  new ArrayList<>();
        LocalDate date = now.toLocalDate();
        int startHour = 7;
        int endHour = 22;
        int maxRepetitions = 2;

        for(int hour = startHour; hour <= endHour; hour++){
            for(int minute =0; minute < 60; minute+= (rd.nextInt(60)+1)){
                LocalTime time = LocalTime.of(hour, minute);
                int repetitions = rd.nextInt(maxRepetitions)+1;

                for(int i=0; i<repetitions; i++){
                    LocalDateTime dateTime = LocalDateTime.of(date, time);
                    dates.add(dateTime.format(formatter));
                }

            }
        }
        return dates;
    }
}
