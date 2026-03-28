package com.igitsarang.ai.rag.controller;

import com.igitsarang.ai.rag.model.Document;
import com.igitsarang.ai.rag.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingest")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping
    public String ingest(
            @RequestParam String url,
            @RequestParam String title,
            @RequestParam String category) {

        ingestionService.ingestAsync(url, title, category);

        return "Ingestion started in background";
    }
}