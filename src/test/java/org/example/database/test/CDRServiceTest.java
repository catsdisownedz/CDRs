package org.example.database.test;

import org.example.database.entity.CDR;
import org.example.database.service.CDRService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CDRServiceTest {

    @Autowired
    private CDRService cdrService;

    @Test
    public void testSaveCDR() {
        CDR cdr = new CDR();
        cdr.setAnum("TEST ");
        cdr.setBnum("SUCCEEDED");
        cdr.setServiceType("sms");
        cdr.setUsage(2.5);
        cdr.setStartDateTime(LocalDateTime.now().toString());

        CDR savedCdr = cdrService.saveCDR(cdr);
        assertNotNull(savedCdr.getId());
    }
}
