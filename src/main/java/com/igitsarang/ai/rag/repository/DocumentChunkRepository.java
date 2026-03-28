package com.igitsarang.ai.rag.repository;

import com.igitsarang.ai.rag.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

    List<DocumentChunk> findByDocumentId(Long documentId);

    @Query(value = """
                SELECT id, document_id, content
                FROM document_chunks
                ORDER BY embedding <-> CAST(:embedding AS vector)
                LIMIT :limit
            """, nativeQuery = true)
    List<Object[]> findTopKSimilarRaw(
            @Param("embedding") String embedding,
            @Param("limit") int limit);
}