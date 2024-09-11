package org.example.display;

import org.example.database.entity.User;
import org.example.formatters.BaseFormatter;
import org.example.utils.TerminalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class LoginMenu {
    private static final String USERS_FILE = "data/users.csv";
    private final BaseFormatter[] formatters;
    private List<User> users;

    @Autowired
    private Menu menu;

    @Autowired
    public LoginMenu(Menu menu, BaseFormatter[] formatters) {
        this.formatters = formatters;
        users = new ArrayList<>();
        loadUsers();
        this.menu = menu;
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
        System.out.println(Color.colorText("Hello, user!", Color.baby_pink));
        System.out.println("1) Login");
        System.out.println("2) New user? Sign up");
        System.out.print("\nChoose an option: ");

        int choice = 0;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println(Color.colorText("Invalid option. Choose 1 or 2.\n", Color.red));
                scanner.next(); // Clear the invalid input
            }
        }

        switch (choice) {
            case 1:
                handleLogin(scanner);
                break;
            case 2:
                handleSignUp(scanner);
                break;
            default:
                System.out.println(Color.colorText("Invalid option. Enter 1 or 2.\n", Color.red));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                display();
                break;
        }
    }


    private void handleLogin(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean authenticated = users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.verifyPassword(password));

        if (authenticated) {
            menu.initialize(username, formatters);
            displayRedirectingMessage();
            menu.display();
        } else {
            System.out.println(Color.colorText("Invalid username or password.", Color.red));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            display();
        }
    }

    private void handleSignUp(Scanner scanner) {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();

        boolean userExists = users.stream()
                .anyMatch(user -> user.getUsername().equals(username));

        if (userExists) {
            System.out.println(Color.colorText("Username already exists.\n", Color.red));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            display();
        } else {
            users.add(new User(username, password));
            saveUserToFile(username, password);
            System.out.println(Color.colorText("User created successfully!\n", Color.green));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Color.colorText("Verify your credentials please", Color.italic_grey));
            handleLogin(scanner);
        }
    }

    private void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayRedirectingMessage() {
        System.out.println("\n");
        String baseMessage = "Redirecting to main menu";
        try {
            for (int i = 0; i < 9; i++) { //9 times
                StringBuilder message = new StringBuilder(baseMessage);
                for (int j = 0; j <= i % 3; j++) { //how many dots and the cycle they go through
                    message.append(".");
                }
                System.out.print(Color.colorText("\r" + message, Color.italic_grey));  // Print message with carriage return to overwrite the line
                Thread.sleep(300);  // 0.4 seconds delay
            }
            TerminalUtils.clearTerminal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

