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
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private long id;
    private String title;
    private List<String> authors = new ArrayList<>();
    private LocalDateTime pub_date;
    private String img;
    private String description;
    private List<String> subject = new ArrayList<>();
    private float rating;

    public void setAuthors(String author) {
        this.authors.add(author);
    }
    public void setSubject(String subject) {
        this.subject.add(subject);
    }
}

