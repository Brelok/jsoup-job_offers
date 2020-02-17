package brelok.workshop;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkFIlter {

    final static String URL_JJO = "https://www.juniorjobsonly.com/jobs?page=";
    final static String URL_BJ = "https://bulldogjob.pl/companies/jobs?page=";
    private static Path path = Paths.get("/Users/brelok/workspace/jsoup junior_jobs_only/offers.txt");
    final static String javascript = "Javascript";

    public static void main(String[] args) {


        List<String> juniorJobsOnly = getLinks_JJO("Wrocław", "java");

        List<String> bulldogJobs = getLinks_BJ("Wrocław", "java");

        List<String> allPortals = new ArrayList<>();
        allPortals.addAll(juniorJobsOnly);
        allPortals.addAll(bulldogJobs);

        try {
            Files.write(path, allPortals);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> getLinks_BJ(String city, String filter) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            List<String> href = new ArrayList<>();

            try {
                Connection.Response homePage = Jsoup.connect(URL_BJ + i)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = homePage.parse();

                Elements elementsLi = document.select("body > .main-content > .app-content.listing > " +
                        ".container > #search-result > .col-sm-10.col-sm-offset-1 > " +
                        ".search-results > .results-list.list-unstyled.content > li");

                for (Element e : elementsLi) {
                    if (!e.is(".results-list-item.subscribe-search")) {

                        if (e.child(0).child(1).child(0).select(".pop-mobile").text().contains(city)) {
                            href.add(e.child(0).child(0).child(0).attr("href"));
                        }
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
        notFoundList.add("Nic nie znaleziono na BulldogsJobs, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }


    private static List<String> getLinks_JJO(String city, String filter) {

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

}
