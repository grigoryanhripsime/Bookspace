package com.bookspace.web.controllers;

import com.bookspace.web.models.User;
import com.bookspace.web.services.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

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
    public String exploreBooks(Model model, HttpSession session, @RequestParam String selection, @RequestParam String search) {
        User user = (User) session.getAttribute("user");
        String images[] = {"profpic.png", "profpic2.png", "profpic3.png", "profpic4.png", "profpic5.png", "profpic6.png", "profpic7.png", "profpic8.png"};
        if (user != null && search != null) {
            model.addAttribute("user", user);
            model.addAttribute("img", "/img/" + images[user.getImg() - 1]);


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
        } else {
            return "error";
        }
    }
}
