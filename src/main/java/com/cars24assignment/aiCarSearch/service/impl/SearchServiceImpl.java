package com.cars24assignment.aiCarSearch.service.impl;

import com.cars24assignment.aiCarSearch.model.SearchFilter;
import com.cars24assignment.aiCarSearch.model.SearchResponse;
import com.cars24assignment.aiCarSearch.service.AIService;
import com.cars24assignment.aiCarSearch.config.Prompt;
import com.cars24assignment.aiCarSearch.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final AIService AIService;
    private final ObjectMapper objectMapper;

    public SearchServiceImpl(AIService AIService, ObjectMapper objectMapper) {
        this.AIService = AIService;
        this.objectMapper = objectMapper;
    }

    @Override
    public SearchResponse search(String query) throws JsonProcessingException {
        log.info("Searching for query {}", query);

        String prompt = Prompt.buildPrompt(query);
        String aiResponse = AIService.askGemini(prompt);

        // parsing response to generate search filter
        SearchFilter searchFilter = parseSearchFilters(aiResponse);

        return null;
    }

    private SearchFilter parseSearchFilters(String response) {
        try {
            return objectMapper.readValue(response, SearchFilter.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}
