package org.example.display;

import org.example.User;

import java.io.*;
import java.util.*;

public class LoginMenu {
    private static final String USERS_FILE = "data/users.csv";
    private List<User> users;

    public LoginMenu() {
        users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, user!");
        System.out.println("1) Login");
        System.out.println("2) New user? Sign up");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                handleLogin(scanner);
                break;
            case 2:
                handleSignUp(scanner);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void handleLogin(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                displayRedirectingMessage();
                new Menu(username).display();
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    private static void displayRedirectingMessage() {
        String baseMessage = "Redirecting to main menu";
        try {
            for (int i = 0; i < 9; i++) {  // Adjust the loop count as needed
                StringBuilder message = new StringBuilder(baseMessage);
                for (int j = 0; j <= i % 3; j++) {
                    message.append(".");
                }
                System.out.print("\r" + message);  // Print message with carriage return to overwrite the line
                Thread.sleep(300);  // 0.4 seconds delay
            }
            System.out.println();  // Move to the next line after the animation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleSignUp(Scanner scanner) {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();

//        if(password.length() < 4){
//            System.out.println("Password must be at least 4 characters.");
//        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        users.add(new User(username, password));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("User created successfully!");
        handleLogin(scanner);
    }
}
