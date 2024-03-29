package com.bookspace.web.repositories;

import com.bookspace.web.models.Comment;
import com.bookspace.web.models.DbBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll();
    List<Comment> findByOpenLibId(String openLibId);
}
