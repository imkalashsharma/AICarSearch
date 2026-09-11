package com.cars24assignment.aiCarSearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @Column(name = "body_type")
    private String bodyType;

    private Integer year;

    private String transmission;

    private Double mileage;

    private Double engine;

    private Double power;

    private Integer seats;

    private Double price;

    @Column(name = "kilometers_driven")
    private Integer kilometersDriven;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "owner_type")
    private String ownerType;

    @Column(name = "new_price")
    private Double newPrice;
}
