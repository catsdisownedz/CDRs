package org.example;

import org.example.database.entity.CDR;
import org.example.database.service.CDRService;
import org.example.display.LoginMenu;
import org.example.formatters.*;
import org.example.utils.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static final String OUTPUT_DIR = "cdr_output";
    private static final Random rd = new Random();
    public static final int NUM_RECORDS = rd.nextInt(300) + 100;
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

        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );

        List<CDR> cdrList = new CopyOnWriteArrayList<>();

        Runnable generateTask = () -> {
            try {
                for (int i = 0; i < NUM_RECORDS; i++) {
                    try {
                        CDR cdr = randomDataGenerator.generateRandomRecord();
                        if (cdrQueue.offer(cdr, 100, TimeUnit.MILLISECONDS)) {
                        } else {
                            i--;
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error generating CDR: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error in generateTask: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };


        Runnable processTask = () -> {
            try {
                while (cdrList.size() < NUM_RECORDS) {
                    CDR cdr = cdrQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (cdr != null) {
                        cdrList.add(cdr);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Processing task interrupted.");
            } finally {
                latch.countDown();
            }
        };

        BaseFormatter[] formatters = {
                new CSVFormatter(),
                new JSONFormatter(),
                new XMLFormatter(),
                new YAMLFormatter(),
        };

        System.out.println("Please wait while we retrieve the files...");


        executorService.execute(generateTask);
        executorService.execute(processTask);


        executorService.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("Task interrupted: " + e.getMessage());
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        try {
            cdrService.saveAllCDRs(cdrList);
            System.out.println("CDRs saved to database successfully.");
        } catch (Exception e) {
            System.err.println("Error saving CDRs to database: " + e.getMessage());
        }

        String[] extensions = {".csv", ".json", ".xml", ".yaml"};

        for (int i = 0; i < formatters.length; i++) {
            String fileName = Paths.get(OUTPUT_DIR, "cdr" + extensions[i]).toString();
            try {
                formatters[i].write(fileName, cdrList);
                System.out.println("Data uploaded into " + extensions[i].substring(1).toUpperCase() + " successfully.");
            } catch (Exception e) {
                System.err.println("Error writing data to " + extensions[i].substring(1).toUpperCase() + " file: " + e.getMessage());
            }
        }

        LoginMenu.displayRedirectingMessage();
        TerminalUtils.clearTerminal();
        new LoginMenu(formatters).display();
    }
}
