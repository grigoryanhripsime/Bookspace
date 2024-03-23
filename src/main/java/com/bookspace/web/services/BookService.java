package com.bookspace.web.services;

import com.bookspace.web.models.Saved;
import com.bookspace.web.repositories.SavedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private SavedRepository savedRepository;

    public BookService(RestTemplate restTemplate, SavedRepository savedRepository) {
        this.restTemplate = restTemplate;
        this.savedRepository = savedRepository;
    }

    public String getBooksGeneral(String query) {
        String apiUrl = "https://openlibrary.org/search.json?limit=20&q=" + query;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                //System.out.println(jsonResponse); // Print JSON response
                return (jsonResponse);
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during the request: " + e.getMessage());
        }
        return (null);
    }

    public String getBooksByAuthor(String author) {
        String apiUrl = "https://openlibrary.org/search.json?limit=25&author=" + author;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                //System.out.println(jsonResponse); // Print JSON response
                return (jsonResponse);
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during the request: " + e.getMessage());
        }
        return (null);
    }

    public String getBooksByTitle(String title) {
        String apiUrl = "https://openlibrary.org/search.json?limit=25&title=" + title;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                //System.out.println(jsonResponse); // Print JSON response
                return (jsonResponse);
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during the request: " + e.getMessage());
        }
        return (null);
    }

    public String getBooksBySubject(String subject) {
        String apiUrl = "https://openlibrary.org/search.json?limit=25&subject=" + subject;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                //System.out.println(jsonResponse); // Print JSON response
                return (jsonResponse);
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during the request: " + e.getMessage());
        }
        return (null);
    }

    public List<String> getOpenLibIdByUserId(Long user_id)
    {
        List<Saved> books = savedRepository.findByUserId(user_id);
        return books.stream().map(Saved::getOpenLibId).toList();
    }
}
