package com.bookspace.web.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BookService {

    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void printBooksByAuthor(String author) {
        String apiUrl = "https://openlibrary.org/search.json?author=" + author;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                System.out.println(jsonResponse); // Print JSON response
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during the request: " + e.getMessage());
        }
    }
}
