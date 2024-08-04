// Main Class
package org.example;

import org.example.formatters.*;
import org.example.utils.DirectoryControls;
import org.example.utils.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String OUTPUT_DIR = "cdr_output";
    private static final int NUM_RECORDS = 10;  // Number of CDR records to generate

    public static void main(String[] args) {
        System.out.println("Starting the program...");

        DirectoryControls dir = new DirectoryControls();

        // Delete the directory if it exists and create a new one
        System.out.println("Deleting and creating directory...");
        dir.deleteDirectory(Paths.get(OUTPUT_DIR));
        dir.createDirectory(OUTPUT_DIR);

        // Generate list of CDRs
        System.out.println("Generating list of CDRs...");
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );
        List<CDR> cdrList = new ArrayList<>();
        for (int i = 0; i < NUM_RECORDS; i++) {
            try {
                cdrList.add(randomDataGenerator.generateRandomRecord());
                System.out.println("Generated record " + (i + 1));
            } catch (Exception e) {
                System.err.println("Error generating record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Formatters
        BaseFormatter[] formatters = {
                new CSVFormatter(),
                new JSONFormatter(),
                new XMLFormatter(),
                new YAMLFormatter(),
        };

        // Formatter file extensions
        String[] extensions = {".csv", ".json", ".xml", ".yaml"};

        // Write formatted CDRs to files
        System.out.println("Writing formatted CDRs to files...");
        for (int i = 0; i < formatters.length; i++) {
            String fileName = Paths.get(OUTPUT_DIR, "cdr" + extensions[i]).toString();
            try {
                formatters[i].write(fileName, cdrList);
                System.out.println("File generated: " + fileName);
            } catch (Exception e) {
                System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Program finished successfully.");
    }
}
