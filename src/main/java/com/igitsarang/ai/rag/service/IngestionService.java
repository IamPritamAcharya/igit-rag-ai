package com.igitsarang.ai.rag.service;

import com.igitsarang.ai.rag.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestionService {

    private final PdfService pdfService;
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    @Async
    public void ingestAsync(String url, String title, String category) {

        if (documentRepository.existsBySourceUrl(url)) {
            log.info("Skipping duplicate (DB): {}", url);
            return;
        }

        String content;

        try {
            if (url.toLowerCase().endsWith(".pdf")) {

                content = pdfService.extractTextFromPdfUrl(url);

                if (content == null || content.trim().isEmpty()) {
                    log.warn("Skipping empty content: {}", url);
                    return;
                }

                if (content.length() < 50) {
                    log.warn("Content too small, skipping: {}", url);
                    return;
                }

            } else {
                content = "HTML not implemented";
            }

        } catch (Exception e) {
            log.error("Failed processing: {}", url);
            return;
        }

        try {
            documentService.createDocument(title, content, url, category);
        } catch (Exception e) {
            log.error("Failed saving document: {}", url);
        }
    }
}