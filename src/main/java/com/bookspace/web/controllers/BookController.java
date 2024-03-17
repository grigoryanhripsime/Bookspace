package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.services.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class BookController {

    private final BookService bookService;
    private List<Book> books = new ArrayList<>();

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/bySubject")
    private void searchBooksBySubject(String subject) {
        System.out.println("This is search by subject, and the subject is: " + subject);
        String jsonResponse = bookService.getBooksBySubject(subject);

        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            getBooks(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("Search was ended");
        books.clear();
    }

    @GetMapping("/byAuthor")
    private void searchBooksByAuthor(String author) {
        System.out.println("This is search by author, and the author is: " + author);
        String jsonResponse = bookService.getBooksByAuthor(author);

        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            getBooks(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("Search was ended");
        books.clear();
    }

    @GetMapping("/byTitle")
    private void searchBooksByTitle(String title) {
        System.out.println("This is search by title, and the title is: " + title);
        String jsonResponse = bookService.getBooksByTitle(title);

        // Parse the JSON string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            getBooks(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
        System.out.println("Search was ended");
        books.clear();
    }

    private void getBooks(JsonNode jsonNode) {
        // Check if the "docs" array is present
        if (!jsonNode.has("docs") || !jsonNode.get("docs").isArray()) {
            System.out.println("No documents found in the JSON response.");
            return;
        }

        // Iterate over the elements in the "docs" array
        for (JsonNode doc : jsonNode.get("docs")) {
            Book book = new Book();

            // Set title if available
            if (doc.has("title")) {
                book.setTitle(doc.get("title").asText());
            }

            // Set authors if available
            if (doc.has("author_name") && doc.get("author_name").isArray()) {
                for (JsonNode authorNode : doc.get("author_name")) {
                    book.setAuthors(authorNode.asText());
                }
            }

            // Set subjects if available
            if (doc.has("subject") && doc.get("subject").isArray()) {
                for (JsonNode subjectNode : doc.get("subject")) {
                    book.setSubject(subjectNode.asText());
                }
            }

            // Set image if available
            if (doc.has("cover_i")) {
                String coverImageUrl = "https://covers.openlibrary.org/b/id/" + doc.get("cover_i").asText() + "-L.jpg";
                book.setImg(coverImageUrl);
            }

            // Set rating if available
            if (doc.has("ratings_average")) {
                book.setRating(doc.get("ratings_average").asLong());
            }

            // Add the book to the list
            books.add(book);
        }
        printBooks();
    }


    private void printBooks(){
        for(Book book : books)
        {
            System.out.println(book);
        }
    }
}

