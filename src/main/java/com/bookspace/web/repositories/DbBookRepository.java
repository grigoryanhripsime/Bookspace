package com.bookspace.web.repositories;

import com.bookspace.web.models.DbBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DbBookRepository extends JpaRepository<DbBook, Long> {
     boolean existsByOpenLibId(String openLibId);
     List<DbBook> findAll();

     @Query("SELECT db FROM DbBook db JOIN Saved sb ON sb.openLibId = db.openLibId WHERE sb.userId = ?1")
     List<DbBook> findByUserId(Long userId);
}
