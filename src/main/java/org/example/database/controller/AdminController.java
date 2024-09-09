package org.example.database.controller;

import org.example.database.entity.CDR;
import org.example.database.entity.User;
import org.example.database.repository.CDRRepository;
import org.example.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final CDRRepository cdrRepository;

    @Autowired
    public AdminController(UserRepository userRepository, CDRRepository cdrRepository) {
        this.userRepository = userRepository;
        this.cdrRepository = cdrRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> users = userRepository.findAll();
        List<CDR> cdrs = cdrRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("cdrs", cdrs);
        return "index";
    }
}
