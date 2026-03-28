package com.igitsarang.ai.rag.service;

import com.igitsarang.ai.rag.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IngestionService {

    private final PdfService pdfService;
    private final DocumentService documentService;

    public Document ingestFromUrl(String url, String title, String category) {

        String content;

        if (url.endsWith(".pdf")) {
            content = pdfService.extractTextFromPdfUrl(url);
        } else {
            content = "HTML content extraction not implemented yet";
        }

        return documentService.createDocument(title, content, url, category);
    }
}