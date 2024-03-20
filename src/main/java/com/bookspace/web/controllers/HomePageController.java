package com.bookspace.web.controllers;

import com.bookspace.web.models.User;
import com.bookspace.web.scrapers.OpenLibraryScraper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomePageController {
    @GetMapping("/homePage")
    public String showHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            model.addAttribute("books", OpenLibraryScraper.trendingBookScraper(session));
            model.addAttribute("links", session.getAttribute("links"));
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
}
