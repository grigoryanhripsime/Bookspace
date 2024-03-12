package com.bookspace.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nickname;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime date;
    private int img;

    @PrePersist
    public void prePersist() {
        // Set the default value for img before persisting the entity
        this.img = generateRandomValue();
    }

    private int generateRandomValue() {
        // Generate a random value between 1 and 8
        Random rand = new Random();
        return rand.nextInt(8) + 1;
    }
}
