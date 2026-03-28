package com.igitsarang.ai.rag.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_chunks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long documentId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "vector(768)")
    private float[] embedding;
}