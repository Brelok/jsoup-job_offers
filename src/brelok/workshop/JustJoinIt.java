package brelok.workshop;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JustJoinIt {

    private final static String URL = "https://justjoin.it";

    public static void main(String[] args) {
        List<String> list = getLinks_JJIT();

    }

    static List<String> getLinks_JJIT (){
        List<String> list = new ArrayList<>();

        try {
            Connection.Response homePage = Jsoup.connect(URL)
                    .method(Connection.Method.GET)
                    .execute();

            Document document = homePage.parse();

            Elements elementsLi = document.select("body > div.main-content > div.sidebar.offers > div.sidebar-inner > ui-view");

            Element element = document.selectFirst("body > .main-content");
            Element element1 = element.child(1);

            System.out.println(element1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
