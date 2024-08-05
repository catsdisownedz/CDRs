package org.example;

import org.example.display.LoginMenu;
import org.example.formatters.*;
import org.example.utils.DirectoryControls;
import org.example.utils.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String OUTPUT_DIR = "cdr_output";
    private static final int NUM_RECORDS = 20;  // Number of CDR records to generate

    public static void main(String[] args) {
        DirectoryControls dir = new DirectoryControls();

        dir.deleteDirectory(Paths.get(OUTPUT_DIR));
        dir.createDirectory(OUTPUT_DIR);

        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );
        List<CDR> cdrList = new ArrayList<>();
        for (int i = 0; i < NUM_RECORDS; i++) {
            cdrList.add(randomDataGenerator.generateRandomRecord());
        }

        BaseFormatter[] formatters = {
                new CSVFormatter(),
                new JSONFormatter(),
                new XMLFormatter(),
                new YAMLFormatter(),
        };

        String[] extensions = {".csv", ".json", ".xml", ".yaml"};

        for (int i = 0; i < formatters.length; i++) {
            String fileName = Paths.get(OUTPUT_DIR, "cdr" + extensions[i]).toString();
            formatters[i].write(fileName, cdrList);
            System.out.println("Data uploaded into " + extensions[i].substring(1).toUpperCase() + " successfully.");
        }

        System.out.println("Redirecting to main menu...");
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new LoginMenu().display();
    }
}
