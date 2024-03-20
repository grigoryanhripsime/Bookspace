package com.bookspace.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
//@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String openLibId;
    private String title;
    private List<String> authors = new ArrayList<>();
    private String pub_date;
    private String publisher;
    private String description;
    private String img;
    private List<String> subject = new ArrayList<>();
    private String language;
    private List<Availability> available = new ArrayList<>();
    private float rating;

    @AllArgsConstructor
    class Availability {
        String shop_name;
        String link;

        @Override
        public String toString() {
            return "Availability{" +
                    "shop_name='" + shop_name + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
    public void setAuthors(String author) {
        this.authors.add(author);
    }
    public void setSubject(String subject) {
        this.subject.add(subject);
    }
    public void setAvailable(String shop_name, String link) {
        this.available.add(new Availability(shop_name, link));
    }
}


