package com.baker.bakerapp.controller;

import com.baker.bakerapp.model.User;
import com.baker.bakerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(@Qualifier("userService") UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegistration() {
        return "regForm";
    }

    @PostMapping("/submit")
    public String addNewUser(User user) {
        userService.createNewUser(user, passwordEncoder.encode(user.getPassword()));
        return "redirect:/auth/login";
    }
}
