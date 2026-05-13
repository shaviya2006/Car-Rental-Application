package com.carrental.car_rental_backend.repository;


import com.carrental.car_rental_backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {


    List<Car> findByStatus(Car.CarStatus status);


    List<Car> findByBrandIgnoreCase(String brand);


    List<Car> findByStatusOrderByDailyPriceAsc(Car.CarStatus status);
}
