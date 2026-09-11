package com.cars24assignment.aiCarSearch.model;

public record SearchFilter(
        String bodyType,
        String fuelType,
        String transmission,
        Long minPriceInr,
        Long maxPriceInr,
        Integer minKilometers,
        Integer maxKilometers,
        Integer minYear,
        Integer maxYear,
        Integer minSeats,
        String city
) {}
