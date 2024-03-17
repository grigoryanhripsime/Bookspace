package com.bookspace.web.controllers;

import com.bookspace.web.services.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/search")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public void searchBooks(@RequestParam(required = false) String author, @RequestParam(required = false) String title, @RequestParam(required = false) String subject) {
        if (author != null && !author.isEmpty()) {
            searchBooksByAuthor(author);
        } else if (title != null && !title.isEmpty()) {
            searchBooksByTitle(title);
        }else if (subject != null && !subject.isEmpty()) {
                searchBooksByGenre(subject);
        } else {
            System.out.println("Neither author nor title specified for search.");
        }
    }
    //search is ready for use
    private void searchBooksByGenre(String subject) {
        System.out.println("This is search by genre");
        String jsonResponse = bookService.getBooksByGenre(subject);

        int i = 0;
        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Iterate over the elements in the "docs" array
            if (jsonNode.has("docs") && jsonNode.get("docs").isArray()) {
                for (JsonNode doc : jsonNode.get("docs")) {
                    // Check if the element has the key "author_name" with value "Agatha Christie"
                    if (doc.has("author_name") && doc.get("author_name").isArray()) {
                        for (JsonNode authorNode : doc.get("author_name")) {
                            // Check if the element has the keys "title" and "author_name"
                            if (doc.has("title") && doc.has("author_name") && doc.has("subject")) {
                                // Iterate over the subjects in the "subject" array
                                for (JsonNode subjectNode : doc.get("subject")) {
                                    // Print the title, author, and genre
                                    System.out.println("title: " + doc.get("title").asText() + ", author: " + authorNode.asText() + ", genre: " + subjectNode.asText());
                                }
                                if (doc.has("cover_i")) {
                                    String coverImageUrl = "https://covers.openlibrary.org/b/id/" + doc.get("cover_i").asText() + "-L.jpg";
                                    System.out.println("Cover Image URL: " + coverImageUrl);
                                }

                            }
                            i++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("i = " + i);
    }

    private void searchBooksByAuthor(String author) {
        System.out.println("This is search by author");
        String jsonResponse = bookService.getBooksByAuthor(author);

        int i = 0;
        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Iterate over the elements in the "docs" array
            if (jsonNode.has("docs") && jsonNode.get("docs").isArray()) {
                for (JsonNode doc : jsonNode.get("docs")) {
                    // Check if the element has the key "author_name" with value "Agatha Christie"
                    if (doc.has("author_name") && doc.get("author_name").isArray()) {

                        for (JsonNode authorNode : doc.get("author_name")) {
                            if (authorNode.asText().equals(author)) {
                                // Check if the element has the keys "title" and "author_name"
                                if (doc.has("title") && doc.has("author_name")) {
                                    // Print the title and the author
                                    System.out.println("title: " + doc.get("title").asText() + ", author: " + authorNode.asText());
                                }
                            }
                            i++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("i = " + i);
    }

    private void searchBooksByTitle(String title) {
        System.out.println("This is search by title");
        String jsonResponse = bookService.getBooksByTitle(title);

        int i = 0;
        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Iterate over the elements in the "docs" array
            if (jsonNode.has("docs") && jsonNode.get("docs").isArray()) {
                for (JsonNode doc : jsonNode.get("docs")) {
                    // Check if the element has the key "author_name" with value "Agatha Christie"
                    if (doc.has("author_name") && doc.get("author_name").isArray()) {

                        for (JsonNode authorNode : doc.get("author_name")) {
                                // Check if the element has the keys "title" and "author_name"
                                if (doc.has("title") && doc.has("author_name")) {
                                    // Print the title and the author
                                    System.out.println("title: " + doc.get("title").asText() + ", author: " + authorNode.asText());
                                }
                            i++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("i = " + i);
    }
}
