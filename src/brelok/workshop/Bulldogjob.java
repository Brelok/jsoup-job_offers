package brelok.workshop;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bulldogjob {

    final static String URL_BJ = "https://bulldogjob.pl/companies/jobs?page=";
    final static String javascript = "Javascript";
    final static int numberOfPages = 7;

    static List<String> getLinks_BJ (String city, String filter) {

        List<String> list = getLinksWithCity(city);

        List<String> filtredList = list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains(filter.toLowerCase()))
                .filter(s -> !s.contains(javascript.toLowerCase()))
                .collect(Collectors.toList());

        List<String> notFoundList = new ArrayList<>();
        notFoundList.add("Nic nie znaleziono na BulldogsJobs, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }

    static List<String> getLinks_BJ (String city, String filterOne, String filterTwo) {
        List<String> list = getLinksWithCity(city);

        List<String> filtredList = list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains(filterOne.toLowerCase()))
                .filter(s -> s.contains(filterTwo.toLowerCase()))
                .filter(s -> !s.contains(javascript.toLowerCase()))
                .collect(Collectors.toList());

        List<String> notFoundList = new ArrayList<>();
        notFoundList.add("Nic nie znaleziono na BulldogsJobs, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }

    private static List<String> getLinksWithCity(String city){
             List<String> list = new ArrayList<>();

        for (int i = 1; i <= numberOfPages; i++) {
            List<String> href = new ArrayList<>();

            try {
                Connection.Response homePage = Jsoup.connect(URL_BJ + i)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = homePage.parse();

                Elements elementsLi = document.select("body > .main-content > .app-content.listing > " +
                        ".container > #search-result > .col-sm-10.col-sm-offset-1 > " +
                        ".search-results > .results-list.list-unstyled.content > li");

                elementsLi.stream()
                        .filter(element -> !element.is(".results-list-item.subscribe-search"))
                        .filter(element -> element.child(0).child(1).child(0).select(".pop-mobile").text().contains(city))
                        .forEach(element -> href.add(element.child(0).child(0).child(0).attr("href")));


            } catch (IOException e) {
                e.printStackTrace();
            }

            list.addAll(href);
        }
        return list;
    }
}
