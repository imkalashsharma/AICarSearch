package com.cars24assignment.aiCarSearch.repository;

import com.cars24assignment.aiCarSearch.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("""
        SELECT v FROM Vehicle v
        WHERE (:bodyType IS NULL OR v.bodyType = :bodyType)
          AND (:fuelType IS NULL OR v.fuelType = :fuelType)
          AND (:transmission IS NULL OR v.transmission = :transmission)
          AND (:minPrice IS NULL OR v.price >= :minPrice)
          AND (:maxPrice IS NULL OR v.price <= :maxPrice)
          AND (:minKm IS NULL OR v.kilometersDriven >= :minKm)
          AND (:maxKm IS NULL OR v.kilometersDriven <= :maxKm)
          AND (:minYear IS NULL OR v.year >= :minYear)
          AND (:maxYear IS NULL OR v.year <= :maxYear)
          AND (:minSeats IS NULL OR v.seats >= :minSeats)
          AND (:city IS NULL OR v.location = :city)
    """)
    List<Vehicle> search(
            String bodyType,
            String fuelType,
            String transmission,
            Long minPrice,
            Long maxPrice,
            Integer minKm,
            Integer maxKm,
            Integer minYear,
            Integer maxYear,
            Integer minSeats,
            String city
    );
}
