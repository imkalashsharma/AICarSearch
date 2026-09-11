package com.cars24assignment.aiCarSearch.service.impl;

import com.cars24assignment.aiCarSearch.entity.Vehicle;
import com.cars24assignment.aiCarSearch.model.SearchFilter;
import com.cars24assignment.aiCarSearch.model.SearchResponse;
import com.cars24assignment.aiCarSearch.repository.VehicleRepository;
import com.cars24assignment.aiCarSearch.service.AIService;
import com.cars24assignment.aiCarSearch.config.Prompt;
import com.cars24assignment.aiCarSearch.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final AIService AIService;
    private final ObjectMapper objectMapper;
    private final VehicleRepository vehicleRepository;

    public SearchServiceImpl(AIService AIService, ObjectMapper objectMapper, VehicleRepository vehicleRepository) {
        this.AIService = AIService;
        this.objectMapper = objectMapper;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public SearchResponse search(String query) throws JsonProcessingException {
        log.info("Searching for query {}", query);

        String prompt = Prompt.buildPrompt(query);
        String aiResponse = AIService.askGemini(prompt);

        // parsing response to generate search filter
        SearchFilter filter = parseSearchFilters(aiResponse);

        // find relevant vehicles
        List<Vehicle> vehicles = vehicleRepository.search(
                filter.bodyType(),
                filter.fuelType(),
                filter.transmission(),
                filter.minPriceInr(),
                filter.maxPriceInr(),
                filter.minKilometers(),
                filter.maxKilometers(),
                filter.minYear(),
                filter.maxYear(),
                filter.minSeats(),
                filter.city()
        );

        return new SearchResponse(vehicles);
    }

    private SearchFilter parseSearchFilters(String response) {
        try {
            return objectMapper.readValue(response, SearchFilter.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}
