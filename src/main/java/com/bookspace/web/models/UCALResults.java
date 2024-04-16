package com.bookspace.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UCALResults {
    String title;
    String author;
    List<Libraries> libraries;
}
