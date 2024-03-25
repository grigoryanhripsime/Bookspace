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
    public static List<Book> trendingBookScraper() {
        List<Book> books = new ArrayList<>();

        try {
            for (int i = 1; i < 5; i++)
            {
                String url = "https://openlibrary.org/trending/daily?page=" + i;
                Document doc = Jsoup.connect(url).get();

                Elements bookElements = doc.select(".searchResultItem");

                for (Element bookElement : bookElements) {
                    Book book = new Book();
                    Elements title_info = bookElement.select(".booktitle");
                    book.setTitle(title_info.select(".results").text());

                    Elements author_info = bookElement.select(".bookauthor");
                    book.setAuthors(author_info.select(".results").text());

                    Element imgElement = bookElement.select("span.bookcover img").first();
                    book.setImg(imgElement.attr("src"));

                    Element bookIdElement = bookElement.select("span.bookcover a").first();
                    String link = bookIdElement.attr("href").substring(7);

                    if (link.indexOf('?') > 0)
                        link = link.substring(0, link.indexOf('?'));
                    book.setOpenLibId(link);

                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (books);
    }

    public static Book detailedBookScrapper(String openLibId) {
        Book book = new Book();
        try {
            String url = "https://openlibrary.org/works/" + openLibId;
            Document doc = Jsoup.connect(url).get();

            //openLibId
            book.setOpenLibId(openLibId);

            //img link
            Element imgElement = doc.select(".bookCover a").first();
            String img = null;
            if (imgElement != null)
                img = "https://" + imgElement.attr("href").substring(2);
            book.setImg(img);

            //title
            Element titleElement = doc.select(".work-title").first();
            String title = titleElement.text();
            book.setTitle(title);

            //author
            Element authorElement = doc.select(".edition-byline").first();
            String author = null;
            for (Element authors : authorElement.select("a")) {
                author = authors.text();
                book.setAuthors(author);
            }
            book.setAuthors(author);

            //pub_date
            Element p_dateElement = doc.select(".edition-omniline-item span").first();
            String pub_date = null;
            if (p_dateElement != null)
                pub_date = p_dateElement.text();
            book.setPub_date(pub_date);

            //publisher
            Element pubElement = doc.select(".edition-omniline-item a").first();
            String publisher = null;
            if (publisher != null)
                publisher = pubElement.text();
            book.setPublisher(publisher);

            //description
            Element descElement = doc.select(".read-more__content p").first();
            String description = null;
            if (descElement != null)
                description = descElement.text();
            book.setDescription(description);

            //subjects
            String subject = null;
            for (Element subjects : doc.select(".clamp a")) {
                subject = subjects.text();
                book.setSubject(subject);
            }
            book.setSubject(subject);

            //language
            Element langElement = doc.select("span[itemprop=\"inLanguage\"] a").first();
            String language = null;
            if (langElement != null)
                language = langElement.text();
            book.setLanguage(language);

            //rating
            Element ratingElement = doc.select(".avg-ratings span[itemprop=ratingValue]").first();
            if (ratingElement != null)
                book.setRating((int) Float.parseFloat(ratingElement.text()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static Book bookScrapper(String openLibId) {
        Book book = new Book();
        try {
            String url = "https://openlibrary.org/works/" + openLibId;
            Document doc = Jsoup.connect(url).get();

            //openLibId
            book.setOpenLibId(openLibId);

            //img link
            Element imgElement = doc.select(".bookCover a").first();
            String img = "https://" + imgElement.attr("href").substring(2);
            book.setImg(img);

            //title
            Element titleElement = doc.select(".work-title").first();
            String title = titleElement.text();
            book.setTitle(title);

            //author
            Element authorElement = doc.select(".edition-byline").first();
            for (Element authors : authorElement.select("a")) {
                String author = authors.text();
                book.setAuthors(author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }
}
