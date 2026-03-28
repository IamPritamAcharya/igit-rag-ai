package com.igitsarang.ai.rag.controller;

import com.igitsarang.ai.rag.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scrape")
@RequiredArgsConstructor
public class ScraperController {

    private final ScraperService scraperService;

    @PostMapping("/notices")
    public String scrapeNotices(@RequestParam String url) {
        scraperService.scrapeAllNotices(url);
        return "Scraping started with pagination...";
    }
}