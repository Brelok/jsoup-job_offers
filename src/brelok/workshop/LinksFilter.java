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

import static brelok.workshop.Bulldogjob.getLinks_BJ;
import static brelok.workshop.JuniorJobsOnly.getLinks_JJO;

public class LinksFilter {

    private static Path path = Paths.get("/Users/brelok/workspace/jsoup junior_jobs_only/offers.txt");

    public static void main(String[] args) {


        List<String> juniorJobsOnly = getLinks_JJO("Wrocław", "java", "junior");

        List<String> bulldogJobs = getLinks_BJ("Wrocław", "java", "junior");

        List<String> allPortals = new ArrayList<>();
        allPortals.addAll(juniorJobsOnly);
        allPortals.addAll(bulldogJobs);

        try {
            Files.write(path, allPortals);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(allPortals);

    }


}
