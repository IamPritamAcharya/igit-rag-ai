package com.igitsarang.ai.rag.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igitsarang.ai.rag.model.Document;
import com.igitsarang.ai.rag.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping("/sample")
    public Document createSample() {
        return service.createSampleDocument();
    }

    @GetMapping
    public List<Document> getAll() {
        return service.getAllDocuments();
    }

}
