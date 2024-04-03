package com.bookspace.web.controllers;

import com.bookspace.web.models.Book;
import com.bookspace.web.models.User;
import com.bookspace.web.repositories.DbBookRepository;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import com.bookspace.web.services.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomePageController {
    @Autowired
    private BookService bookService;

    @Autowired
    private DbBookRepository dbBookRepository;

    @GetMapping("/homePage")
    public String showHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            //setting user information
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);

            //the most trending book
            Book firstTrendingBook = OpenLibraryScraper.detailedBookScrapper("OL21692056W");
            model.addAttribute("firstTrendingBook", firstTrendingBook);
            System.out.println(firstTrendingBook);


            //explore new trending books
            model.addAttribute("exploreBooks", dbBookRepository.findAll());

            //user library books
            model.addAttribute("libBooks", dbBookRepository.findByUserId(user.getId()));
            return "homePage";
        } else {
            return "error";
        }
    }
    @PostMapping("/generalSearch")
    public String generalSearch(@RequestParam String search)
    {
        search = search.replace(" ", "+");
        System.out.println("Search by: " + search);
        return "redirect:/search/general?query=" + search;
    }

    @GetMapping("/")
    public String generalPage(Model model, HttpSession session)
    {
        //the most trending book
        Book firstTrendingBook = OpenLibraryScraper.detailedBookScrapper("OL21692056W");
        model.addAttribute("firstTrendingBook", firstTrendingBook);
        System.out.println(firstTrendingBook);

        //explore new trending books
        model.addAttribute("exploreBooks", dbBookRepository.findAll());

        return "first";
    }
}
