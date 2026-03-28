package com.igitsarang.ai.rag.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LLMService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:11434/api/generate";

    public String generateAnswer(String prompt) {

        Map<String, Object> request = Map.of(
                "model", "llama3",
                "prompt", prompt,
                "stream", false
        );

        Map response = restTemplate.postForObject(URL, request, Map.class);

        return (String) response.get("response");
    }
}