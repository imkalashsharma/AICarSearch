package com.cars24assignment.aiCarSearch.controller;

import com.cars24assignment.aiCarSearch.model.SearchRequest;
import com.cars24assignment.aiCarSearch.model.SearchResponse;
import com.cars24assignment.aiCarSearch.service.ImportService;
import com.cars24assignment.aiCarSearch.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final ImportService importService;

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) throws JsonProcessingException {
        SearchResponse response = searchService.search(request.query());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/vehicles/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) throws IOException {
        String result = importService.importCsv(file);

        return ResponseEntity.ok(result);
    }
}
