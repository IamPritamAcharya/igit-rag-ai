package com.igitsarang.ai.rag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igitsarang.ai.rag.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    boolean existsBySourceUrl(String sourceUrl);
}