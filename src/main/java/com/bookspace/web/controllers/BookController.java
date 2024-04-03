package com.bookspace.web.controllers;

import com.bookspace.web.models.*;
import com.bookspace.web.repositories.CommentRepository;
import com.bookspace.web.repositories.SavedRepository;
import com.bookspace.web.repositories.UserRepository;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import com.bookspace.web.services.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SavedRepository savedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookService bookService;

    List<Availability> available;

    void setAvailable(Book book)
    {
        available = new ArrayList<>();
        AvailabilityController availabilityController = new AvailabilityController(book.getOpenLibId(), bookService);
        available.add(new Availability("amazon", availabilityController.amazonLink()));
        available.add(new Availability("goodreads", availabilityController.goodreadsLink()));
        available.add(new Availability("libthing", availabilityController.libthingLink()));
    }

    @PostMapping("/details")
    public String detailedBook(Model model, HttpSession session, @RequestParam String openLibId)
    {
        User user = (User) session.getAttribute("user");
        String userImages[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + userImages[user.getImg() - 1]);
            System.out.println(openLibId);
            Book book = OpenLibraryScraper.detailedBookScrapper(openLibId);
            session.setAttribute("book", book);
            model.addAttribute("book", book);

            boolean recordExists = savedRepository.existsByUserIdAndOpenLibId(user.getId(), openLibId);
            model.addAttribute("saved", recordExists);

            setAvailable(book);
            model.addAttribute("amazon", available.get(0).getLink());
            model.addAttribute("goodreads", available.get(1).getLink());
            model.addAttribute("libthing", available.get(2).getLink());

            //getting comments for this book
            List<Comment> comments =  commentRepository.findByOpenLibId(book.getOpenLibId());
            List<printableComment> printableComments = new ArrayList<>();

            for (Comment comment : comments) {
                User commentedUser = userRepository.findById(comment.getUserId()).orElse(null);

                if (user != null && book != null)
                    printableComments.add(new printableComment(comment.getComment(), "/img/" + userImages[commentedUser.getImg() - 1], commentedUser.getNickname()));
            }
            model.addAttribute("comments", printableComments);
            return "bookDetails";
        }
        else
            return "error";
    }

    @GetMapping("/details")
    public String detailedBookOverwrite(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        Book book = (Book) session.getAttribute("book");
        String userImages[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + userImages[user.getImg() - 1]);
            model.addAttribute("book", book);

            boolean recordExists = savedRepository.existsByUserIdAndOpenLibId(user.getId(), book.getOpenLibId());
            model.addAttribute("saved", recordExists);

            setAvailable(book);
            model.addAttribute("amazon", available.get(0));
            model.addAttribute("goodreads", available.get(1));
            model.addAttribute("libthing", available.get(2));

            //getting comments for this book
            List<Comment> comments =  commentRepository.findByOpenLibId(book.getOpenLibId());
            List<printableComment> printableComments = new ArrayList<>();

            for (Comment comment : comments) {
                User commentedUser = userRepository.findById(comment.getUserId()).orElse(null);

                if (user != null && book != null)
                    printableComments.add(new printableComment(comment.getComment(), "/img/" + userImages[commentedUser.getImg() - 1], commentedUser.getNickname()));
            }
            model.addAttribute("comments", printableComments);
            return "bookDetails";
        }
        else
            return "error";
    }
}
