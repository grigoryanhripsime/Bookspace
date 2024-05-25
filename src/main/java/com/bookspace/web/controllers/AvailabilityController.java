package com.bookspace.web.controllers;

import com.bookspace.web.services.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AvailabilityController {
    BookService bookService;
    String jsonResponse;
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;

    public static boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000); // Set timeout
            connection.setReadTimeout(3000);
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 299);
        } catch (IOException e) {
            return false;
        }
    }

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
            JsonNode amazon = doc.path("id_amazon");
            for (JsonNode st : amazon)
            {
                String id = st.asText();
                System.out.println(id);
                String amazonLink = "https://www.amazon.com/dp/" + id;
                System.out.println(amazonLink);
                if (isValidURL(amazonLink))
                    return amazonLink;
            }
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
            JsonNode goodreads = doc.path("id_goodreads");
            for (JsonNode st : goodreads)
            {
                String id = st.asText();
                System.out.println(id);
                String goodreadsLink = "https://www.goodreads.com/book/show/" + id;
                System.out.println(goodreadsLink);
                if (isValidURL(goodreadsLink))
                    return goodreadsLink;
            }
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
            JsonNode goodreads = doc.path("id_librarything");
            for (JsonNode st : goodreads)
            {
                String id = st.asText();
                System.out.println(id);
                String libthingLink = "https://www.librarything.com/work/" + id;
                System.out.println(libthingLink);
                if (isValidURL(libthingLink))
                    return libthingLink;
            }
        }
        return null;
    }
}
