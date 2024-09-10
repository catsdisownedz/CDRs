package org.example.database.service;

import org.example.database.entity.User;
import org.example.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> value.verifyPassword(password)).orElse(false);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<User> saveAllUsers(List<User> users) {
        List<User> existingUsers = userRepository.findAll();
        for (User user : users) {
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

            if (existingUser.isPresent()) {
                User dbUser = existingUser.get();
                if (dbUser.getPassword().equals(user.getPassword())) {
                    System.out.println("User " + user.getUsername() + " already exists with the same password. Skipping...");
                } else {
                    updatePassword(dbUser, user.getPassword());
                    System.out.println("User " + user.getUsername() + " password updated.");
                }
            } else {
                User savedUser = userRepository.save(user);
                existingUsers.add(savedUser);
                System.out.println("User " + user.getUsername() + " added to the database.");
            }
        }
        return existingUsers;
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void displayUsers(List<User> users) {
        // Print the header for the user display
        System.out.printf("%-5s | %-20s | %-20s\n", "ID", "Username", "Password");
        System.out.println("---------------------------------------------------------------");

        // Print each user's details
        for (User user : users) {
            System.out.printf("%-5d | %-20s | %-20s\n",
                    user.getId(), user.getUsername(), user.getPassword());
        }
    }

}
