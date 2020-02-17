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

public class JuniorJobsOnly {

    final static String URL_JJO = "https://www.juniorjobsonly.com/jobs?page=";
    final static String javascript = "Javascript";

    static List<String> getLinks_JJO(String city, String filter) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            List<String> href = new ArrayList<>();

            try {
                Connection.Response homePage = Jsoup.connect(URL_JJO + i)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = homePage.parse();

                Elements elementsA = document.select(".board > a");

                for (Element e : elementsA) {
                    if (e.child(0).child(2).child(1).text().contains(city)) {
                        href.add(e.attr("href"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.addAll(href);
        }

        List<String> filtredList = list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains(filter.toLowerCase()))
                .filter(s -> !s.contains(javascript.toLowerCase()))
                .collect(Collectors.toList());

        List<String> notFoundList = new ArrayList<>();
        notFoundList.add("Nic nie znaleziono na Junior Jobs Only, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }

    static List<String> getLinks_JJO(String city, String filterOne, String filterTwo){
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            List<String> href = new ArrayList<>();

            try {
                Connection.Response homePage = Jsoup.connect(URL_JJO + i)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = homePage.parse();

                Elements elementsA = document.select(".board > a");

                for (Element e : elementsA) {
                    if (e.child(0).child(2).child(1).text().contains(city)) {
                        href.add(e.attr("href"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.addAll(href);
        }

        List<String> filtredList = list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains(filterOne.toLowerCase()))
                .filter(s -> s.contains(filterTwo.toLowerCase()))
                .filter(s -> !s.contains(javascript.toLowerCase()))
                .collect(Collectors.toList());

        List<String> notFoundList = new ArrayList<>();
        notFoundList.add("Nic nie znaleziono na Junior Jobs Only, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }
}

