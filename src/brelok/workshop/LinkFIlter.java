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
    private static Path path = Paths.get("/Users/brelok/workspace/jsoup junior_jobs_only/offers.txt");

    public static void main(String[] args) {

        List<String> juniorJobsOnly = getLinks("Poznań", "java");

        try {
            Files.write(path, juniorJobsOnly);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static List<String> getLinks(String city, String filter) {

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
                .collect(Collectors.toList());

        List<String> notFoundList = new ArrayList<>();
        notFoundList.add("Nic nie znaleziono, zmień parametry");

        if (filtredList.size() < 1) return notFoundList;
        else {
            return filtredList;
        }
    }
}
