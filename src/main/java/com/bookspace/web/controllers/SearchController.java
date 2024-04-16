package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.models.UCALResults;
import com.bookspace.web.repositories.LibrariesRepository;
import com.bookspace.web.scrapers.UCALScrapper;
import com.bookspace.web.services.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final BookService bookService;
    private List<Book> books;
    private List<UCALResults> UCALbooks;
    @Autowired
    private LibrariesRepository librariesRepository;


    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(value = "/general", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    private String searchBooksGeneral(String query, HttpSession session) {
        System.out.println("This is search by query, and the query is: " + query);
//        books = new ArrayList<>();
//        String jsonResponse = bookService.getBooksGeneral(query);
//
//        // Parse the JSON string into a JSON object
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//            getBooks(jsonNode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
//        }

        //search in Union Catalog of Armenian Libraries
        System.out.println("Search in UCAL");
        UCALbooks = UCALScrapper.UCALScrapping(query, librariesRepository);


        System.out.println("Search was ended");
        session.setAttribute("books", books);
        session.setAttribute("UCALbooks", UCALbooks);
        return "redirect:/searchResults";
    }

    @GetMapping("/bySubject")
    private String searchBooksBySubject(String subject, HttpSession session) {
        System.out.println("This is search by subject, and the subject is: " + subject);
        books = new ArrayList<>();
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
        session.setAttribute("books", books);
        return "redirect:/searchResults";
    }

    @GetMapping("/byAuthor")
    private String searchBooksByAuthor(String author, HttpSession session) {
        books = new ArrayList<>();
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
        session.setAttribute("books", books);
        return "redirect:/searchResults";
    }

    @GetMapping("/byTitle")
    private String searchBooksByTitle(String title, HttpSession session) {
        books = new ArrayList<>();
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
        session.setAttribute("books", books);
        return "redirect:/searchResults";
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

            //Set openLibId if available
            if (doc.has("key")) {
                book.setOpenLibId(doc.get("key").asText().substring(7));
            }

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
            if (book.getImg() != null)
                books.add(book);
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

