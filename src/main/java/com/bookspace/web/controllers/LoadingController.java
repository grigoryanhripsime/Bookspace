package com.bookspace.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoadingController {

    @GetMapping("/")
    public String redirectToLoadingPage() {
        // Redirect to the loading page
        return "redirect:/loading.html";
    }

    // This is just a placeholder method to simulate data loading
    @GetMapping("/data")
    public String loadData() {
        // Simulate loading data
        try {
            Thread.sleep(5000); // Simulate a delay of 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Redirect to the target page after loading data
        return "redirect:/target-page.html";
    }
}
