package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.models.User;
import com.bookspace.web.repositories.SavedRepository;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import com.bookspace.web.services.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    @Autowired
    private SavedRepository savedRepository;

    @Autowired
    BookService bookService;

    @PostMapping("/details")
    public String detailedBook(Model model, HttpSession session, @RequestParam String openLibId)
    {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            System.out.println(openLibId);
            Book book = OpenLibraryScraper.detailedBookScrapper(openLibId);
            session.setAttribute("book", book);
            model.addAttribute("book", book);

            boolean recordExists = savedRepository.existsByUserIdAndOpenLibId(user.getId(), openLibId);
            model.addAttribute("saved", recordExists);

            AvailabilityController availabilityController = new AvailabilityController(book.getOpenLibId(), bookService);
            model.addAttribute("amazon", availabilityController.amazonLink());
            model.addAttribute("goodreads", availabilityController.goodreadsLink());
            model.addAttribute("libthing", availabilityController.libthingLink());
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
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            model.addAttribute("book", book);
            boolean recordExists = savedRepository.existsByUserIdAndOpenLibId(user.getId(), book.getOpenLibId());
            model.addAttribute("saved", recordExists);

            AvailabilityController availabilityController = new AvailabilityController(book.getOpenLibId(), bookService);
            model.addAttribute("amazon", availabilityController.amazonLink());
            model.addAttribute("goodreads", availabilityController.goodreadsLink());
            model.addAttribute("libthing", availabilityController.libthingLink());
            return "bookDetails";
        }
        else
            return "error";
    }
}
