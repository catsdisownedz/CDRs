package org.example.database.service;

import org.example.database.entity.CDR;
import org.example.database.repository.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CDRService {

    @Autowired
    private CDRRepository cdrRepository;

    @Transactional
    public CDR saveCDR(CDR cdr) {
        return cdrRepository.save(cdr);
    }

    public List<CDR> getAllCDRs() {
        return cdrRepository.findAll();
    }

    public List<CDR> getCDRsByANUM(String name) {
        return cdrRepository.findByANUM(name);
    }
    public List<CDR> getCDRsByBNUM(String name) {
        return cdrRepository.findByBNUM(name);
    }
    public List<CDR> findByANUMOrBNUM(String num) {
        return cdrRepository.findByANUM(num).isEmpty() ?
                cdrRepository.findByBNUM(num) :
                cdrRepository.findByANUM(num);
    }

    public void saveAllCDRs(List<CDR> cdrList) {
        cdrRepository.saveAll(cdrList);
    }

    public void displayCDRs(List<CDR> cdrs) {
        System.out.printf("%-10s | %-10s | %-10s | %-20s | %-10s\n",
                "ANUM", "BNUM", "Service", "Start Time", "Usage");
        System.out.println("---------------------------------------------------------------");

        for (CDR cdr : cdrs) {
            System.out.printf("%-10s | %-10s | %-10s | %-20s | %-10.2f\n",
                    cdr.getAnum(), cdr.getBnum(), cdr.getServiceType(),
                    cdr.getStartDateTime().toString(), cdr.getUsage());
        }
    }

}
