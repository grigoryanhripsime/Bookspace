package com.bookspace.web.controllers;

import com.bookspace.web.models.UCALResults;
import com.bookspace.web.models.User;
import com.bookspace.web.models.Book;
import com.bookspace.web.repositories.DbBookRepository;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ExploreController {
    @Autowired
    private DbBookRepository dbBookRepository;
    @GetMapping("/explore")
    public String showExplorePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            //explore new trending books
            model.addAttribute("books", dbBookRepository.findAll());
            return "explore";
        } else {
            return "error";
        }
    }

    @PostMapping("/explore")
    public String exploreBooks(@RequestParam String selection, @RequestParam String search) {
        System.out.println("Selecttion: " + selection);
        System.out.println("Search: " + search);

        try {
            String encodedSearch = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());
            String redirectUrl = "/search/general?query=" + encodedSearch;
            System.out.println("Redirecting to: " + redirectUrl);
            search = search.replace(" ", "+");
            System.out.println(search);
            if (selection.equals("Title"))
                return "redirect:/search/byTitle?title=" + search;
            if (selection.equals("Author"))
                return "redirect:/search/byAuthor?author=" + search;
            if (selection.equals("Genre"))
                return "redirect:/search/bySubject?subject=" + search;
            return "redirect:" + redirectUrl;
        } catch (UnsupportedEncodingException e) {
            // Handle encoding exception
            e.printStackTrace();
            return "error"; // Or handle the exception according to your application's logic
        }
    }

    @GetMapping("/searchResults")
    public String searchResults(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        List<Book> books = (List<Book>) session.getAttribute("books");
        List<UCALResults> UCALbooks = (List<UCALResults>) session.getAttribute("UCALbooks");

        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            model.addAttribute("books", books);
            model.addAttribute("UCALbooks", UCALbooks);
            return "searchRes";
        }
        System.out.println("I'm here");
        return "error";
    }
}
