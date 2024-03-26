package com.bookspace.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="db_books")
public class DbBook {
    @Id
    private String openLibId;
    @Column(name="img_link")
    private String img;
    private String title;
    private String author;
}
