package com.cars24assignment.aiCarSearch.service;

import com.cars24assignment.aiCarSearch.model.SearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchService {
    SearchResponse search(String query) throws JsonProcessingException;
}
