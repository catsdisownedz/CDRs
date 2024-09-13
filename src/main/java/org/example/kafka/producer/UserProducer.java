package org.example.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendUser(String userData) {
        String traceId = UUID.randomUUID().toString();
        ProducerRecord<String, String> record = new ProducerRecord<>("users-topic", null, traceId, userData, null);
        kafkaTemplate.send(record);
    }
}

