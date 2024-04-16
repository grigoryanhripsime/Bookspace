package com.bookspace.web.repositories;

import com.bookspace.web.models.Libraries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibrariesRepository extends JpaRepository<Libraries, Long> {
    List<Libraries> findAll();
}