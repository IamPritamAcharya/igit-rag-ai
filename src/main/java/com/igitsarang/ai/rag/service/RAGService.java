package com.igitsarang.ai.rag.service;

import com.igitsarang.ai.rag.model.DocumentChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final SearchService searchService;
    private final LLMService llmService;

    public String ask(String question) {

        List<DocumentChunk> chunks = searchService.search(question);

        StringBuilder context = new StringBuilder();

        for (DocumentChunk chunk : chunks) {
            context.append("----\n");
            context.append(chunk.getContent()).append("\n");
        }

        String prompt = """
                You are an assistant for INDIRA GANDHI INSTITUTE OF TECHNOLOGY, sarang, odisha.

                Use the context below to answer the question.
                If partial information is available, try to answer as best as possible.

                Context:
                %s

                Question:
                %s
                """.formatted(context.toString(), question);

        // 4. Generate answer
        return llmService.generateAnswer(prompt);
    }
}