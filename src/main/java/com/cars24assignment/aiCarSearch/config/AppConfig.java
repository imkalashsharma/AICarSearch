package com.cars24assignment.aiCarSearch.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {
    @Value("${ai.apiKey}")
    private String aiKey;
}
