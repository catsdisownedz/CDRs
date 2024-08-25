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
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Random rd = new Random();
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static List<String> randomStartDateTime(LocalDateTime datee) {
        List<Callable<List<String>>> tasks = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        LocalDate date = datee.toLocalDate();
        int startHour = 0;
        int endHour = 23;
        int maxRepetitions = rd.nextInt(7) + 1;

        for (int hour = startHour; hour <= endHour; hour++) {
            final int currentHour = hour;
            tasks.add(() -> {
                List<String> hourDates = new ArrayList<>();
                for (int minute = 0; minute < 60; minute += (rd.nextInt(30) + 1)) {
                    LocalTime time = LocalTime.of(currentHour, minute);
                    int repetitions = rd.nextInt(maxRepetitions) + 1;
                    for (int i = 0; i < repetitions; i++) {
                        LocalDateTime dateTime = LocalDateTime.of(date, time);
                        hourDates.add(dateTime.format(formatter));
                    }
                }
                return hourDates;
            });
        }

        try {
            List<Future<List<String>>> futures = executorService.invokeAll(tasks);
            List<String> dates = futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            return dates;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            executorService.shutdown();
        }
    }
}
