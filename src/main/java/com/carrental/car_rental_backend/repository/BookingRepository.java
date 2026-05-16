package com.carrental.car_rental_backend.repository;

import com.carrental.car_rental_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {


    List<Booking> findByUserId(Integer userId);


    List<Booking> findByStatus(Booking.BookingStatus status);
}