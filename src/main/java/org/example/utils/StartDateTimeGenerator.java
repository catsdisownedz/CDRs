package org.example.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class StartDateTimeGenerator {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Random rd = new Random();
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static List<String> randomStartDateTime(LocalDateTime datee) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        LocalDate date = datee.toLocalDate();
        int startHour = 0;
        int endHour = 23;
        int maxRepetitions = rd.nextInt(7) + 1;

        // Initialize an array to hold results in order
        List<String>[] results = new ArrayList[endHour - startHour + 1];
        for (int i = 0; i < results.length; i++) {
            results[i] = new ArrayList<>();
        }

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int hour = startHour; hour <= endHour; hour++) {
            final int currentHour = hour;
            tasks.add(() -> {
                List<String> hourDates = new ArrayList<>();
                for (int minute = 0; minute < 60; minute += (rd.nextInt(15) + 1)) {
                    LocalTime time = LocalTime.of(currentHour, minute);
                    int repetitions = rd.nextInt(maxRepetitions) + 1;
                    for (int i = 0; i < repetitions; i++) {
                        LocalDateTime dateTime = LocalDateTime.of(date, time);
                        hourDates.add(dateTime.format(formatter));
                    }
                }
                // Store the results in the corresponding index
                synchronized (results) {
                    results[currentHour - startHour].addAll(hourDates);
                }
                return null;
            });
        }

        try {
            executorService.invokeAll(tasks);
            // Flatten the list and maintain order
            List<String> dates = new ArrayList<>();
            for (List<String> hourList : results) {
                dates.addAll(hourList);
            }
            return dates;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            System.err.println("Date generation interrupted: " + e.getMessage());
            return new ArrayList<>();
        } finally {

            executorService.shutdown();
            //System.out.println("generation ended");
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {

                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

    }
}
