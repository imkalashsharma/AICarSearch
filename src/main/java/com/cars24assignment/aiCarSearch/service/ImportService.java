package com.cars24assignment.aiCarSearch.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportService {
    String importCsv(MultipartFile file) throws IOException;
}
