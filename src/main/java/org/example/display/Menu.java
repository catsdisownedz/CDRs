package org.example.display;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Menu {
    private String username;

    public Menu(String username) {
        this.username = username;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hello, " + username + "!");
            System.out.println("1) View Data files");
            System.out.println("2) Filter results by");
            System.out.println("3) View Service type volume");
            System.out.println("4) Revenue calculator");
            System.out.println("5) Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewDataFiles(scanner);
                    break;
                case 2:
                    filterResultsBy(scanner);
                    break;
                case 3:
                    viewServiceTypeVolume(scanner);
                    break;
                case 4:
                    revenueCalculator(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void viewDataFiles(Scanner scanner) {
        System.out.println("Choose file type to view:");
        System.out.println("1) XML");
        System.out.println("2) YML");
        System.out.println("3) CSV");
        System.out.println("4) JSON");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        String extension = "";

        switch (choice) {
            case 1: extension = "xml"; break;
            case 2: extension = "yaml"; break;
            case 3: extension = "csv"; break;
            case 4: extension = "json"; break;
            default: System.out.println("Invalid option."); return;
        }

        try {
            File file = new File("cdr_output/cdr." + extension);
            if (file.exists()) {
                java.awt.Desktop.getDesktop().open(file);
            } else {
                System.out.println("File does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterResultsBy(Scanner scanner) {
        System.out.println("Filter results by:");
        System.out.println("1) Alphabetical order");
        System.out.println("2) Service type");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                // Implement alphabetical order filtering
                break;
            case 2:
                System.out.println("Filter by service type:");
                System.out.println("1) SMS");
                System.out.println("2) CALL");
                System.out.println("3) DATA");
                int typeChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                // Implement service type filtering based on typeChoice
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void viewServiceTypeVolume(Scanner scanner) {
        System.out.println("View service type volume:");
        System.out.println("1) Call");
        System.out.println("2) SMS");
        System.out.println("3) Data");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Implement service type volume view based on choice
    }

    private void revenueCalculator(Scanner scanner) {
        System.out.println("Revenue calculator:");
        System.out.println("1) Today");
        System.out.println("2) Yesterday");
        System.out.println("3) Other");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                // Implement today's revenue calculation
                break;
            case 2:
                // Implement yesterday's revenue calculation
                break;
            case 3:
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                // Implement revenue calculation for the given date
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }
}
