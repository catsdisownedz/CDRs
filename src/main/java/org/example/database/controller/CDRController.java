package org.example.database.controller;

import org.example.database.entity.CDR;
import org.example.database.repository.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cdr")
public class CDRController {

    @Autowired
    private CDRRepository cdrRepository;

    @GetMapping
    public List<CDR> getAllCDRs() {
        return cdrRepository.findAll();
    }

    @PostMapping
    public CDR createCDR(@RequestBody CDR cdr) {
        return cdrRepository.save(cdr);
    }
}
