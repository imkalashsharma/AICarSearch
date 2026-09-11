package com.cars24assignment.aiCarSearch.controller;

import com.cars24assignment.aiCarSearch.model.SearchRequest;
import com.cars24assignment.aiCarSearch.model.SearchResponse;
import com.cars24assignment.aiCarSearch.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/v1")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) throws JsonProcessingException {
        SearchResponse response = searchService.search(request.query());

        return ResponseEntity.ok(response);
    }
}
