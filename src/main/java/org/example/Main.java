package org.example;

import org.example.database.entity.CDR;
import org.example.database.entity.User;
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
    @Autowired
    private UserService userService;

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
                    try {

                        CDR cdr = randomDataGenerator.generateRandomRecord();
                        cdrQueue.put(cdr);
                        //System.out.println(cdr + "added to queue: " +i);
                    } catch (Exception e) {
                        i--;
                    }
                }
            } finally {
                latch.countDown();
            }
        };

        List<CDR> cdrList = new ArrayList<>();
        Runnable processTask = () -> {
            try {
                while (cdrList.size() < NUM_RECORDS || !cdrQueue.isEmpty()) {
                    CDR cdr = cdrQueue.poll(5000, TimeUnit.MILLISECONDS);
                    if (cdr != null) {
                        cdrList.add(cdr);
                        //System.out.println("Added CDR to list, size: " + cdrList.size());
                    }
                    else {
                        System.out.println("Polling timeout occurred, queue might be empty");
                    }
                }
            } catch (Exception e) {
                System.err.println("Process task was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        };

        multiThreader.runUserTask(generateTask);
        multiThreader.runUserTask(processTask);

        try {
            latch.await();
            multiThreader.shutdown();
            if (!multiThreader.executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                multiThreader.executorService.shutdownNow();
                if (!multiThreader.executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
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
        List<User> users = new ArrayList<>(CSVFormatter.extractUsersFromCSV("data/users.csv"));


        try {
            cdrService.saveAllCDRs(cdrList);
            System.out.println("CDRs saved to database successfully");
            userService.saveAllUsers(users);
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
