package com.cars24assignment.aiCarSearch.model;

import jakarta.validation.constraints.NotBlank;

public record SearchRequest(
      @NotBlank String query
) {}
