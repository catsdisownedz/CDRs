package org.example.display;

import org.example.User;
import org.example.formatters.BaseFormatter;
import org.example.utils.TerminalUtils;

import java.io.*;
import java.util.*;

public class LoginMenu {
    private static final String USERS_FILE = "data/users.csv";
    private final BaseFormatter[] formatters;
    private List<User> users;

    public LoginMenu(BaseFormatter[] formatters) {
        this.formatters = formatters;
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
        System.out.println(Color.colorText("Hello, user!", Color.baby_pink));
        System.out.println("1) Login");
        System.out.println("2) New user? Sign up");
        System.out.print("\nChoose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleLogin(scanner);
                break;
            case 2:
                handleSignUp(scanner);
                break;
            default:
                System.out.println(Color.colorText("Invalid option.", Color.red));
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
                Menu main = new Menu(username, formatters);
                main.display();
                return;
            }
        }
        System.out.println(Color.colorText("Invalid username or password.", Color.red));
        display();
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

    private void handleSignUp(Scanner scanner) {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();


        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println(Color.colorText("Username already exists.", Color.red));
                display();
            }
        }

        users.add(new User(username, password));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Color.colorText("User created successfully!",Color.green));
        handleLogin(scanner);
    }
}
