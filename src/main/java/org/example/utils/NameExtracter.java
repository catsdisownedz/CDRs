package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class NameExtracter {
    public static List<String> readNamesFromFile(String filePath, int numNames) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (numNames > names.size()) {
            throw new IllegalArgumentException("There have not been this much registered users today." +
                    "\nChoose a number within the " + names.size() + " names in today's transactions.");
        }

        return names.parallelStream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collectedNames -> {
                    Collections.shuffle(collectedNames);
                    return collectedNames.stream().limit(numNames).collect(Collectors.toList());
                }));
    }
}
