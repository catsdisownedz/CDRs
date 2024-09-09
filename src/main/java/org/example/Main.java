package org.example;

import org.example.database.entity.CDR;
import org.example.database.service.*;
import org.example.display.*;
import org.example.formatters.*;
import org.example.utils.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static final String OUTPUT_DIR = "cdr_output";
    private static final Random rd = new Random();
    public static int NUM_RECORDS = rd.nextInt(100) + 100;
    private final BlockingQueue<CDR> cdrQueue = new LinkedBlockingQueue<>(NUM_RECORDS);

    @Autowired
    private CDRService cdrService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DirectoryControls dir = new DirectoryControls();
        dir.deleteDirectory(Paths.get(OUTPUT_DIR));
        dir.createDirectory(OUTPUT_DIR);


        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );

       // System.out.println("generation resumed");

        System.out.println("Please wait while we retrieve the " + NUM_RECORDS +  " files...");
        MultiThreader multiThreader = new MultiThreader();
        CountDownLatch latch = new CountDownLatch(2); // For two tasks

        Runnable generateTask = () -> {
            try {
                for (int i = 0; i < NUM_RECORDS; i++) {
                    System.out.println("EL TRY EYE numbar" + i );
                    try {
                        System.out.println("abl generation");
                        CDR cdr = randomDataGenerator.generateRandomRecord();
                        System.out.println("QUEUE SIZE: " + cdrQueue.size());
                        cdrQueue.put(cdr);
                        System.out.println(cdr + "added to queue: " +i);
                    } catch (Exception e) {
                        System.out.println("ana bayez 1");
                        i--;
                        System.out.println("ana bayez 2");
                    }
                    System.out.println("EL TRY EYE COMPLETED "+ i);
                }
                System.out.println("generateTask completed");
            } finally {
                System.out.println("entered finally of generateTask");
                latch.countDown(); // Signal that the generateTask is complete
            }
        };

        List<CDR> cdrList = new ArrayList<>();
        Runnable processTask = () -> {
            try {
                while (cdrList.size() < NUM_RECORDS || !cdrQueue.isEmpty()) {
                    CDR cdr = cdrQueue.poll(5000, TimeUnit.MILLISECONDS);
                    if (cdr != null) {
                        cdrList.add(cdr);
                        System.out.println("Added CDR to list, size: " + cdrList.size());
                    }
                    else {
                        System.out.println("Polling timeout occurred, queue might be empty");
                    }
                }
            } catch (Exception e) {
                System.err.println("Process task was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Preserve interrupt status
            } finally {
                System.out.println("latch 2 completed");
                latch.countDown(); // Signal that the processTask is complete
            }
        };

        multiThreader.runUserTask(generateTask);
        multiThreader.runUserTask(processTask);

        try {
            System.out.println("before latch waiting");
            latch.await();
            System.out.println("after latch waiting");// Wait for both tasks to complete
            multiThreader.shutdown(); // Gracefully shutdown after tasks are complete
            if (!multiThreader.executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("error 1");
                multiThreader.executorService.shutdownNow();
                if (!multiThreader.executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("forcing shutdown");
            multiThreader.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }


        BaseFormatter[] formatters = {
                new CSVFormatter(),
                new JSONFormatter(),
                new XMLFormatter(),
                new YAMLFormatter(),
        };


        String[] extensions = {".csv", ".json", ".xml", ".yaml"};

        try {
            System.out.println("trying to save to database");
            cdrService.saveAllCDRs(cdrList);
            System.out.println("CDRs saved to database successfully");
        } catch (Exception e) {
            System.err.println("Error saving CDRs to database: " + e.getMessage());
            e.printStackTrace();
        }

        for (int i = 0; i < formatters.length; i++) {
            String fileName = Paths.get(OUTPUT_DIR, "cdr" + extensions[i]).toString();
            formatters[i].write(fileName, cdrList);
            System.out.println("Data uploaded into " + extensions[i].substring(1).toUpperCase() + " successfully.");
        }

        LoginMenu.displayRedirectingMessage();
        TerminalUtils.clearTerminal();
        new LoginMenu(formatters).display();
    }
}
