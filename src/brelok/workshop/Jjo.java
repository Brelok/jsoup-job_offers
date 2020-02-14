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

public class Jjo {

    final static String URL_JJO = "https://www.juniorjobsonly.com/jobs?page=";

    public static void main(String[] args) {

        List<String> allHrefs = getHrefsFromCity("Wrocław");

        List<String> filtredList = getOnlyFilteredOffers(allHrefs, "java");


    }

    private static List<String> getOnlyFilteredOffers(List<String> list, String filter) {
        return list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains(filter))
                .collect(Collectors.toList());

    }

    private static List<String> getHrefsFromCity(String city) {

        List<String> allHrefs = new ArrayList<>();

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

                allHrefs.addAll(href);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allHrefs;
    }
}
