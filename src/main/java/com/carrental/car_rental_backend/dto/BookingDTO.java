package com.carrental.car_rental_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Integer id;
    private Integer userId;
    private Integer carId;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status;

    private BigDecimal totalAmount;
}