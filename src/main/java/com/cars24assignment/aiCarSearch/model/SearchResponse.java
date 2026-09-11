package com.cars24assignment.aiCarSearch.model;

import com.cars24assignment.aiCarSearch.entity.Vehicle;

import java.util.List;

public record SearchResponse (List<Vehicle> vehicles) {}
