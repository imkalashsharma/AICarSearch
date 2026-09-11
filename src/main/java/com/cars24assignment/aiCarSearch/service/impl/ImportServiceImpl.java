package com.cars24assignment.aiCarSearch.service.impl;

import com.cars24assignment.aiCarSearch.entity.Vehicle;
import com.cars24assignment.aiCarSearch.repository.VehicleRepository;
import com.cars24assignment.aiCarSearch.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final VehicleRepository vehicleRepository;

    public String importCsv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty");
        }

        try (
                Reader reader = new InputStreamReader(file.getInputStream())
        ) {

            CSVParser parser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            List<Vehicle> vehicles = new ArrayList<>();

            for (CSVRecord row : parser) {
                Vehicle vehicle = new Vehicle();

                vehicle.setName(row.get("name"));
                vehicle.setLocation(emptyToNull(row.get("location")));
                vehicle.setBodyType(row.get("body_type"));

                vehicle.setYear(parseInt(row.get("year")));
                vehicle.setKilometersDriven(parseInt(row.get("kilometers_driven")));

                vehicle.setFuelType(row.get("fuel_type"));
                vehicle.setTransmission(row.get("transmission"));
                vehicle.setOwnerType(row.get("owner_type"));

                vehicle.setMileage(parseDouble(row.get("mileage")));
                vehicle.setEngine(parseDouble(row.get("engine")));
                vehicle.setPower(parseDouble(row.get("power")));
                vehicle.setSeats(parseInt(row.get("seats")));
                vehicle.setNewPrice(parseDouble(row.get("new_price")));
                vehicle.setPrice(parseDouble(row.get("price")));

                vehicles.add(vehicle);      // add new vehicle
            }

            vehicleRepository.saveAll(vehicles);    // bulk save

            return "Import Successful!";
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV", e);
        }
    }

    private Integer parseInt(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (int) Double.parseDouble(value.trim());
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Double.parseDouble(value.trim());
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
