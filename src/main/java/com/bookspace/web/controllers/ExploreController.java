package com.bookspace.web.controllers;

import com.bookspace.web.models.User;
import com.bookspace.web.models.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class ExploreController {
    @GetMapping("/explore")
    public String showExplorePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            // Add any additional information to the model that you want to display on the home page
            return "explore";
        } else {
            return "error";
        }
    }

    @PostMapping("/explore")
    public String exploreBooks(@RequestParam String selection, @RequestParam String search) {
        System.out.println("Selecttion: " + selection);
        System.out.println("Search: " + search);

        search = search.replace(" ", "+");
        System.out.println(search);
        if (selection.equals("Title"))
            return "redirect:/search/byTitle?title=" + search;
        if (selection.equals("Author"))
            return "redirect:/search/byAuthor?author=" + search;
        if (selection.equals("Genre"))
            return "redirect:/search/bySubject?subject=" + search;
        return "error";
    }

    @GetMapping("/searchResults")
    public String searchResults(Model model, HttpSession session)
    {
        System.out.println("Hellooooooo");
        User user = (User) session.getAttribute("user");
        List<Book> books = (List<Book>) session.getAttribute("books");

        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);
            model.addAttribute("books", books);
            return "searchRes";
        }
        System.out.println("I'm here");
        return "error";
    }
}
