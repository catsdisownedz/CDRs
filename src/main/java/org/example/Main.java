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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class Main {
    public static final String OUTPUT_DIR = "cdr_output";
    private static final Random rd = new Random();
    public static int NUM_RECORDS = rd.nextInt(300)+100;
    private static final BlockingQueue<CDR> cdrQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        DirectoryControls dir = new DirectoryControls();
        dir.deleteDirectory(Paths.get(OUTPUT_DIR));
        dir.createDirectory(OUTPUT_DIR);


        MultiThreader multiThreader = new MultiThreader();
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );

        Runnable generateTask = () -> {
            for (int i = 0; i < NUM_RECORDS; i++) {
                try {
                    CDR cdr = randomDataGenerator.generateRandomRecord();
                    cdrQueue.put(cdr);
                } catch (IllegalArgumentException | InterruptedException e) {
                    i--;
                }
            }
        };

        List<CDR> cdrList = new ArrayList<>();


        Runnable processTask = () -> {
            while (cdrList.size() < NUM_RECORDS) {
                try {
                    CDR cdr = cdrQueue.poll(55, TimeUnit.MILLISECONDS);
                    if (cdr != null) {
                        cdrList.add(cdr);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        BaseFormatter[] formatters = {
                new CSVFormatter(),
                new JSONFormatter(),
                new XMLFormatter(),
                new YAMLFormatter(),
        };


        System.out.println("Please wait while we retrieve the files...");


        multiThreader.runUserTask(generateTask);
        multiThreader.runUserTask(processTask);


        multiThreader.shutdown();
        try {
            if (!multiThreader.executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                multiThreader.executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            multiThreader.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        String[] extensions = {".csv", ".json", ".xml", ".yaml"};

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