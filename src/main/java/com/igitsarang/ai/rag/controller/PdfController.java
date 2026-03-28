package com.igitsarang.ai.rag.controller;

import com.igitsarang.ai.rag.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/extract")
    public String extractText(@RequestParam String url) {
        return pdfService.extractTextFromPdfUrl(url);
    }
}