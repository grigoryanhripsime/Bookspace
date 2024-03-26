package com.bookspace.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "saved_books")
public class Saved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String openLibId;
    private long userId;

    public Saved(String openLibId, long userId) {
        this.openLibId = openLibId;
        this.userId = userId;
    }
}
