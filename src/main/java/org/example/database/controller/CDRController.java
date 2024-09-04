package org.example.database.controller;

import org.example.database.entity.CDR;
import org.example.database.service.CDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CDRController {

    @Autowired
    private CDRService cdrService;

    @GetMapping("/cdrs")
    public List<CDR> getAllCDRs() {
        return cdrService.getAllCDRs();
    }

    @PostMapping("/cdrs")
    public CDR createCDR(@RequestBody CDR cdr) {
        return cdrService.saveCDR(cdr);
    }
}
