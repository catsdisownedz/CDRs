package org.example.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CDRProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCDR(String cdrData) {
        // Example trace metadata, this would typically be more complex and integrated
        String traceId = generateTraceId(); // Custom method to generate a trace ID
        ProducerRecord<String, String> record = new ProducerRecord<>("cdrs", null, traceId, cdrData, null);
        kafkaTemplate.send(record);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
