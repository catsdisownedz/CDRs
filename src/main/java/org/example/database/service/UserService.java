package org.example.database.service;

import org.example.database.entity.User;
import org.example.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


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
    public void displayUsers(List<User> users) {
        System.out.printf("%-5s | %-20s | %-20s\n", "ID", "Username", "Password");
        System.out.println("-------------------------------------------------------");

        for (User user : users) {
            System.out.printf("%-5d | %-20s | %-20s\n",
                    user.getId(), user.getUsername(), user.getPassword());
        }
    }

    public List<User> getAllUsers() {
         return userRepository.findAll();
    }
}
