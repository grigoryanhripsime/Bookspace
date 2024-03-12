package com.bookspace.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BookService {

    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private final String API_KEY;

    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate, @Value("${google.books.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.API_KEY = apiKey;
    }

    public String searchBooks(String query) {
        // Build the URL for the Google Books API search endpoint
        String apiUrl = UriComponentsBuilder.fromUriString(GOOGLE_BOOKS_API_URL)
                .queryParam("q", query)
                .queryParam("key", API_KEY)
                .toUriString();

        // Make the GET request and return the response as a String
        return restTemplate.getForObject(apiUrl, String.class);
    }

    // Add more methods to interact with the Google Books API as needed

}


