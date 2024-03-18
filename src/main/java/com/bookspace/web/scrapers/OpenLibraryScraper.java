package com.bookspace.web.scrapers;

import com.bookspace.web.models.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryScraper {

    static List<Book> books;
    public static List<Book> bookScraper() {
        books = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://openlibrary.org/trending/daily?page=1").get();
            Elements bookElements = doc.select(".searchResultItem");

            for (Element bookElement : bookElements) {
                Book book = new Book();
                Elements title_info = bookElement.select(".booktitle");
                book.setTitle(title_info.select(".results").text());

                Elements author_info = bookElement.select(".bookauthor");
                book.setAuthors(author_info.select(".results").text());

                Element imgElement = bookElement.select("span.bookcover img").first();
                book.setImg(imgElement.attr("src"));

                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Document doc = Jsoup.connect("https://openlibrary.org/trending/daily?page=2").get();
            Elements bookElements = doc.select(".searchResultItem");

            for (Element bookElement : bookElements) {
                Book book = new Book();
                Elements title_info = bookElement.select(".booktitle");
                book.setTitle(title_info.select(".results").text());

                Elements author_info = bookElement.select(".bookauthor");
                book.setAuthors(author_info.select(".results").text());

                Element imgElement = bookElement.select("span.bookcover img").first();
                book.setImg(imgElement.attr("src"));

                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (books);
    }
}
