package org.example.utils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NameExtracter {

    private static final Random rd = new Random();

    public static List<String> readNamesFromFile(String filePath, int numNames) {
        List<String> names = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            long fileLength = raf.length();
            int maxAttempts = numNames * 2; //to control the attempts at unique names!

            for (int i = 0; i < maxAttempts && names.size() < numNames; i++) {
                long randomPosition = Math.abs(rd.nextLong()) % fileLength;
                raf.seek(randomPosition);
                raf.readLine();

                String line = raf.readLine();
                if (line != null && !names.contains(line.trim())) {
                    names.add(line.trim());
                }
            }

            //if not enough unique names were found, we fallback to reading sequentially
            if (names.size() < numNames) {
                raf.seek(0);
                String line;
                while ((line = raf.readLine()) != null && names.size() < numNames) {
                    if (!names.contains(line.trim())) {
                        names.add(line.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (numNames > names.size()) {
            throw new IllegalArgumentException("There are not enough unique names in the file.");
        }
        Collections.shuffle(names);

        return names;
    }
}
