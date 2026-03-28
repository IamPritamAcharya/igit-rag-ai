package com.igitsarang.ai.rag.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:11434/api/embeddings";

    public float[] generateEmbedding(String text) {

        Map<String, Object> request = Map.of(
                "model", "nomic-embed-text",
                "prompt", text);

        Map response = restTemplate.postForObject(URL, request, Map.class);

        List<Double> embeddingList = (List<Double>) response.get("embedding");

        float[] embedding = new float[embeddingList.size()];

        for (int i = 0; i < embeddingList.size(); i++) {
            embedding[i] = embeddingList.get(i).floatValue();
        }

        return embedding;
    }
}