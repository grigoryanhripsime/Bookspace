package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.models.Comment;
import com.bookspace.web.models.DbBook;
import com.bookspace.web.models.User;
import com.bookspace.web.repositories.CommentRepository;
import com.bookspace.web.repositories.DbBookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    DbBookRepository dbBookRepository;
    @Autowired
    CommentRepository commentRepository;

    @PostMapping("addComment")
    public String addComment(HttpSession session, @RequestParam String comment)
    {
        User user = (User) session.getAttribute("user");
        Book book = (Book) session.getAttribute("book");

        //save in db_books
        if (!dbBookRepository.existsByOpenLibId(book.getOpenLibId())) {
            dbBookRepository.save(new DbBook(book.getOpenLibId(), book.getImg(), book.getTitle(), book.getAuthors().get(0)));
        }

        //save the comment in Comment table
        commentRepository.save(new Comment(book.getOpenLibId(), user.getId(), comment));
        return "redirect:/details";
    }
}
