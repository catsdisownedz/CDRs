package org.example;

import org.example.display.LoginMenu;
import org.example.formatters.*;
import org.example.utils.DirectoryControls;
import org.example.utils.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final String OUTPUT_DIR = "cdr_output";
    private static Random rd = new Random();
    public static int NUM_RECORDS = 60;

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
            try {
                cdrList.add(randomDataGenerator.generateRandomRecord());
            } catch (IllegalArgumentException e) {
                i--;
            }
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

        LoginMenu.displayRedirectingMessage();
        TerminalUtils.clearTerminal();
        new LoginMenu(formatters).display();
    }
}
