package org.example.utils;

import java.io.*;
import java.util.*;

public class NameExtracter {

    private static final Random rd = new Random();
    private static final int MAX_RETRIES = 3; // Number of times to retry if unique names are insufficient

    public static List<String> readNamesFromFile(String filePath, int numNames) {
        List<String> names = new ArrayList<>();
        Set<String> uniqueNames = new LinkedHashSet<>(); // Use LinkedHashSet to preserve order and uniqueness
        boolean namesSufficient = false;

        for (int retry = 0; retry < MAX_RETRIES && !namesSufficient; retry++) {
            try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
                long fileLength = raf.length();
                int maxAttempts = numNames * 4; // Control attempts at unique names

                for (int i = 0; i < maxAttempts && uniqueNames.size() < numNames; i++) {
                    long randomPosition = Math.min(Math.abs(rd.nextLong()) % fileLength, fileLength - 1);
                    raf.seek(randomPosition);
                    raf.readLine(); // Skip partial line

                    String line = raf.readLine();
                    if (line != null) {
                        uniqueNames.add(line.trim());
                    }
                }

                // If not enough unique names were found, fallback to reading sequentially
                if (uniqueNames.size() < numNames) {
                    raf.seek(0);
                    String line;
                    while ((line = raf.readLine()) != null && uniqueNames.size() < numNames) {
                        uniqueNames.add(line.trim());
                    }
                }

                namesSufficient = uniqueNames.size() >= numNames;
                names = new ArrayList<>(uniqueNames);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (namesSufficient) {
                System.out.println("Successfully extracted " + uniqueNames.size() + " unique names.");
            } else {
                System.out.println("Retrying... Attempt " + (retry + 1) + " of " + MAX_RETRIES);
            }
        }

        if (!namesSufficient) {
            System.out.println("Warning: Unable to find the requested number of unique names. Found " + uniqueNames.size() + " names.");
        }

        Collections.shuffle(names); // Shuffle if needed

        return names;
    }
}
