package com.carrental.car_rental_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal dailyPrice;
    private String fuelType;
    private String type;
    private String status;
}