package org.example.database.service;


import org.example.database.entity.CDR;
import org.example.database.repository.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CDRService {

    @Autowired
    private CDRRepository cdrHistoryRepository;

    public List<CDR> getCDRHistory(LocalDateTime date) {
        List<CDR> history = cdrHistoryRepository.findByStartDateTime(date);
        if (history.isEmpty()) {
            // Generate CDRs and save to history
            generateAndSaveCDR(date);
            history = cdrHistoryRepository.findByStartDateTime(date);
        }
        return history;
    }

    private void generateAndSaveCDR(LocalDateTime date) {
        // Your logic to generate CDRs
        // Example:
        //CDR cdr = new CDR("username", "sd","sd",4.523, "call", "10 minutes");
        //cdrHistoryRepository.save(cdr);
    }
    public void deleteCDRHistory(Long id) {
        cdrHistoryRepository.deleteById(id);
    }

}
