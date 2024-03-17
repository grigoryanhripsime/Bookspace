package com.bookspace.web.controllers;

import com.bookspace.web.models.User;
import com.bookspace.web.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "logIn";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (email.isEmpty() || password.isEmpty()) {
            return "error";
        }

        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute("user", user); // Store user in session
            return "redirect:/homePage"; // Redirect to the homePage endpoint
        } else {
            return "error";
        }
    }
}
