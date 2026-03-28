package com.igitsarang.ai.rag.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.igitsarang.ai.rag.model.Document;
import com.igitsarang.ai.rag.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repository;
    private final ChunkingService chunkingService;

    public Document createSampleDocument() {

        Document doc = Document.builder()
                .title("Exam Notice 2026")
                .content("""
                        Semester exams will start from March 25, 2026. Students must carry ID cards.
                        Late entry will not be allowed. All candidates must report 30 minutes before exam.
                        Use of unfair means will lead to disqualification. Mobile phones are strictly prohibited.
                        Practical exams will be scheduled separately. Follow all instructions carefully.
                        """)
                .sourceUrl("https://igitsarang.ac.in/notice/2026")
                .category("notice")
                .createdAt(LocalDateTime.now())
                .build();

        Document savedDoc = repository.save(doc);

        chunkingService.chunkAndSave(savedDoc);

        return savedDoc;
    }

    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    public Document createDocument(String title, String content, String url, String category) {

        Document doc = Document.builder()
                .title(title)
                .content(content)
                .sourceUrl(url)
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();

        Document saved = repository.save(doc);

        chunkingService.chunkAndSave(saved);

        return saved;
    }
}
