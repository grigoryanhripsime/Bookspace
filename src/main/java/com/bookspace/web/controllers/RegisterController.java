package com.bookspace.web.controllers;

import com.bookspace.web.models.User;
import com.bookspace.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "createAcc";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Validate the form data (you can add validation logic here)
        if (user.getNickname().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return "error";
        }

        // Save the user to the database
        userRepository.save(user);

        // You can add any additional logic or redirect to another page
        return "redirect:/login";
    }
}
