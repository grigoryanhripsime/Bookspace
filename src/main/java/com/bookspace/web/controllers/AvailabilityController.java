package com.bookspace.web.controllers;

import com.bookspace.web.services.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AvailabilityController {
    BookService bookService;
    String jsonResponse;
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;
    public AvailabilityController(String bookId, BookService bookService) {
        this.jsonResponse = bookService.getBookByID(bookId);
        try {
            jsonNode = objectMapper.readTree(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during JSON parsing: " + e.getMessage());
        }
    }
    public String amazonLink(){
        if (!jsonNode.has("docs")) {
            System.out.println("No documents found in the JSON response.");
            return "error";
        }
        JsonNode doc = jsonNode.get("docs").get(0);
        System.out.println(doc);
        // Set amazonLink if available
        if (doc.has("id_amazon")) {
            String amazonLink = "https://www.amazon.com/dp/" + doc.get("id_amazon").get(0).asText();
            return amazonLink;
        }
        return null;
    }
    public String goodreadsLink(){
        if (!jsonNode.has("docs")) {
            System.out.println("No documents found in the JSON response.");
            return "error";
        }
        JsonNode doc = jsonNode.get("docs").get(0);
        // Set amazonLink if available
        if (doc.has("id_goodreads")) {
            String goodreadsLink = "https://www.goodreads.com/book/show/" + doc.get("id_goodreads").get(0).asText();
            return goodreadsLink;
        }
        return null;
    }
    public String libthingLink(){
        if (!jsonNode.has("docs")) {
            System.out.println("No documents found in the JSON response.");
            return "error";
        }
        JsonNode doc = jsonNode.get("docs").get(0);
        // Set amazonLink if available
        if (doc.has("id_librarything")) {
            String libthingLink = "https://www.librarything.com/work/" + doc.get("id_librarything").get(0).asText();
            return libthingLink;
        }
        return null;
    }
}
