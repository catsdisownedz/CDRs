// DirectoryControls Class
package org.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.awt.Desktop;

public class DirectoryControls {
    public void createDirectory(String dirName) {
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void deleteDirectory(Path path) {
        if (Files.exists(path)) {
            try {
                Files.walk(path)
                        .map(Path::toFile)
                        .forEach(File::delete);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //unused function, doesn't really work
    public static void openFile(String filePath) {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("File does not exist: " + filePath);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while trying to open the file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop operations are not supported on this system.");
        }
    }
}
