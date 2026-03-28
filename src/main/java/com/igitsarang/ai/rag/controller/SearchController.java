package com.igitsarang.ai.rag.controller;

import com.igitsarang.ai.rag.model.DocumentChunk;
import com.igitsarang.ai.rag.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<DocumentChunk> search(@RequestParam String query) {
        return searchService.search(query);
    }
}