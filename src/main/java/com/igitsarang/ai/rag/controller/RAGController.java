package com.igitsarang.ai.rag.controller;

import com.igitsarang.ai.rag.service.RAGService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ask")
@RequiredArgsConstructor
public class RAGController {

    private final RAGService ragService;

    @GetMapping
    public String ask(@RequestParam String question) {
        return ragService.ask(question);
    }
}