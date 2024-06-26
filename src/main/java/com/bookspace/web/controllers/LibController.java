package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.models.Saved;
import com.bookspace.web.models.User;
import com.bookspace.web.models.DbBook;
import com.bookspace.web.repositories.DbBookRepository;
import com.bookspace.web.repositories.SavedRepository;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import com.bookspace.web.services.BookService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LibController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SavedRepository savedRepository;

    @Autowired
    private DbBookRepository dbBookRepository;

    @GetMapping("/myLib")
    public String myLib(HttpSession session, Model model)
    {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            //user library books
            model.addAttribute("books", dbBookRepository.findByUserId(user.getId()));
            return "myLib";
        }
        else
            return "error";
    }
    @PostMapping("/save")
    public String save(@RequestParam String openLibId, HttpSession session, Model model)
    {
        User user = (User) session.getAttribute("user");
        Book book = (Book) session.getAttribute("book");

        //save in db_books
        if (!dbBookRepository.existsByOpenLibId(openLibId)) {
            dbBookRepository.save(new DbBook(book.getOpenLibId(), book.getImg(), book.getTitle(), book.getAuthors().get(0)));
        }

        //save in saved_books
        savedRepository.save(new Saved(book.getOpenLibId(), user.getId()));
        System.out.println("Book was saved");
//        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};
//        model.addAttribute("user", user);
//        model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
//        model.addAttribute("book", book);
        return "redirect:/details";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteItem(@RequestParam String openLibId, HttpSession session) {
        // Retrieve user ID from session
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();

        // Delete the item from the database
        savedRepository.deleteByUserIdAndOpenLibId(userId, openLibId);

        // Redirect back to the page displaying saved items
        return "redirect:/myLib";
    }
}
