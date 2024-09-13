package org.example.kafka.consumer;

import org.example.kafka.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UsageEmailNotifier {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "users-topic", groupId = "email-group")
    public void processUserData(String userData) {
        // Extract user email and usage data
        // Assume userData is JSON and convert it accordingly
        String userEmail = extractEmail(userData); // Implement this method based on your data format
        String usageData = extractUsage(userData); // Same as above

        // Construct the email message
        String message = "Dear User, your usage for this period is: " + usageData;

        // Send email
        emailService.sendEmail(userEmail, "Your Usage Receipt", message);
    }

    private String extractEmail(String userData) {
        // Dummy implementation
        return "zeina.18@hotmail.com";
    }

    private String extractUsage(String userData) {
        // Dummy implementation
        return "1024 MB";
    }
}

