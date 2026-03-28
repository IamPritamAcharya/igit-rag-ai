package com.igitsarang.ai.rag.service;

import com.igitsarang.ai.rag.model.DocumentChunk;
import com.igitsarang.ai.rag.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository repository;

    public List<DocumentChunk> search(String query) {

        float[] embedding = embeddingService.generateEmbedding(query);
        String vectorString = toVectorString(embedding);

        List<Object[]> results = repository.findTopKSimilarRaw(vectorString, 5);

        return results.stream().map(row -> DocumentChunk.builder()
                .id(((Number) row[0]).longValue())
                .documentId(((Number) row[1]).longValue())
                .content((String) row[2])
                .build()).toList();
    }

    private String toVectorString(float[] embedding) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            sb.append(embedding[i]);
            if (i < embedding.length - 1)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}