package com.bookspace.web.scrapers;

import com.bookspace.web.models.Libraries;
import com.bookspace.web.models.UCALResults;
import com.bookspace.web.repositories.LibrariesRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UCALScrapper {
    public static List<UCALResults> UCALScrapping(String query, LibrariesRepository librariesRepository) {
        List<UCALResults> books = new ArrayList<>();
        try {
            String url = "https://armunicat.nla.am/cgi-bin/koha/opac-search.pl?q=" + query + "&weight_search=1";
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            Elements bookElements = doc.select(".title_summary");
            for (Element element : bookElements) {
                UCALResults ucalResults = new UCALResults();
                ucalResults.setTitle(element.select(".title").text());
                ucalResults.setAuthor(element.select(".author").text());
                String libraries = element.select(".ItemBranch").text();
                List<Libraries> libraries1 = librariesRepository.findAll();
                List<Libraries> toShow = new ArrayList<>();
                for (Libraries lib : libraries1)
                    if (libraries.contains(lib.getName()))
                        toShow.add(lib);
                ucalResults.setLibraries(toShow);
                books.add(ucalResults);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(books);
        return books;
    }
}