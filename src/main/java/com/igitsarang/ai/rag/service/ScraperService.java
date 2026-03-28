package com.igitsarang.ai.rag.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScraperService {

    private final IngestionService ingestionService;

    public void scrapeAllNotices(String baseUrl) {

        System.out.println("Scraping ALL notices from single page...");

        Document doc;

        try {
            doc = Jsoup.connect(baseUrl).get();
        } catch (Exception e) {
            System.out.println(" Failed to load page");
            return;
        }

        Elements rows = doc.select("#table_notice tbody tr");

        if (rows.isEmpty()) {
            System.out.println(" No rows found!");
            return;
        }

        System.out.println(" Total rows found: " + rows.size());

        // Track duplicates in same run
        Set<String> seenUrls = new HashSet<>();

        for (Element row : rows) {

            Elements cols = row.select("td");

            if (cols.size() < 3)
                continue;

            String title = cols.get(0).text().trim();
            String date = cols.get(1).text().trim();

            Element linkElement = cols.get(2).selectFirst("a");

            if (linkElement == null)
                continue;

            String pdfUrl = linkElement.absUrl("href");

            System.out.println("Found: " + title + " | " + date + " -> " + pdfUrl);

            // Skip duplicates in same run
            if (seenUrls.contains(pdfUrl)) {
                System.out.println(" Already seen in this run: " + pdfUrl);
                continue;
            }

            seenUrls.add(pdfUrl);

            try {
                ingestionService.ingestAsync(
                        pdfUrl,
                        title.isEmpty() ? "Notice" : title,
                        "notice");
            } catch (Exception ex) {
                System.out.println(" Error ingesting: " + pdfUrl);
            }
        }

        System.out.println(" Scraping completed!");
    }
}