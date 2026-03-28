package com.igitsarang.ai.rag.service;

import com.igitsarang.ai.rag.model.Document;
import com.igitsarang.ai.rag.model.DocumentChunk;
import com.igitsarang.ai.rag.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChunkingService {

    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;

    private static final int MAX_CHUNK_LENGTH = 300;
    private static final int OVERLAP = 50;

    public List<DocumentChunk> chunkAndSave(Document document) {

        String content = document.getContent();

        // 1. Split into sentences
        String[] sentences = content.split("(?<=\\.)");

        List<DocumentChunk> chunks = new ArrayList<>();
        StringBuilder currentChunk = new StringBuilder();

        for (String sentence : sentences) {

            if (currentChunk.length() + sentence.length() > MAX_CHUNK_LENGTH) {

                // Save chunk
                chunks.add(createChunk(document, currentChunk.toString()));

                // Add overlap
                String overlapText = getOverlapText(currentChunk.toString());
                currentChunk = new StringBuilder(overlapText);
            }

            currentChunk.append(sentence.trim()).append(" ");
        }

        // Save last chunk
        if (!currentChunk.isEmpty()) {
            chunks.add(createChunk(document, currentChunk.toString()));
        }

        return chunkRepository.saveAll(chunks);
    }

    private DocumentChunk createChunk(Document doc, String text) {

        float[] embedding = embeddingService.generateEmbedding(text);

        return DocumentChunk.builder()
                .documentId(doc.getId())
                .content(text.trim())
                .embedding(embedding)
                .build();
    }

    private String getOverlapText(String text) {
        if (text.length() <= OVERLAP)
            return text;

        String overlapPart = text.substring(text.length() - OVERLAP);

        // Find first space to avoid cutting words
        int firstSpace = overlapPart.indexOf(" ");
        if (firstSpace != -1) {
            overlapPart = overlapPart.substring(firstSpace + 1);
        }

        return overlapPart;
    }
}