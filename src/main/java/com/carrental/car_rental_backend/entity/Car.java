package com.carrental.car_rental_backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private BigDecimal dailyPrice;

    private String fuelType;

    private String type;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.AVAILABLE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum CarStatus {
        AVAILABLE,
        RENTED,
        MAINTENANCE
    }
}
