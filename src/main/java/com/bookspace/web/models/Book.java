package com.bookspace.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private String openLibId;
    private String title;
    private List<String> authors = new ArrayList<>();
    private String pub_date;
    private String publisher;
    private String description;
    private String img;
    private List<String> subject = new ArrayList<>();
    private String language;
    private float rating;

    public void setAuthors(String author) {
        this.authors.add(author);
    }
    public void setSubject(String subject) {
        this.subject.add(subject);
    }
}


