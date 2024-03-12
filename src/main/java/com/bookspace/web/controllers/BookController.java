package com.bookspace.web.controllers;

import com.bookspace.web.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        String searchResults = bookService.searchBooks(query);
        model.addAttribute("searchResults", searchResults);

        // Assuming "searchResults" is a JSON string, you might want to parse it into a more usable format

        return "searchResults"; // Thymeleaf template name (searchResults.html)
    }
}

