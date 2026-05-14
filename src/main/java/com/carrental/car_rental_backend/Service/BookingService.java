package com.carrental.car_rental_backend.Service;


import com.carrental.car_rental_backend.dto.BookingDTO;
import com.carrental.car_rental_backend.entity.*;
import com.carrental.car_rental_backend.repository.BookingRepository;
import com.carrental.car_rental_backend.repository.CarRepository;
import com.carrental.car_rental_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;


    public BookingDTO createBooking(Integer userId, Integer carId, Booking bookingDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));


        if (!car.getStatus().equals(Car.CarStatus.AVAILABLE)) {
            throw new RuntimeException("Car is not available");
        }


        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStartDate(bookingDetails.getStartDate());
        booking.setEndDate(bookingDetails.getEndDate());
        booking.setStatus(Booking.BookingStatus.PENDING);


        long days = ChronoUnit.DAYS.between(
                bookingDetails.getStartDate(),
                bookingDetails.getEndDate()
        );
        booking.setTotalAmount(car.getDailyPrice().multiply(java.math.BigDecimal.valueOf(days)));

        Booking saved = bookingRepository.save(booking);
        return convertToDTO(saved);
    }


    public List<BookingDTO> getUserBookings(Integer userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public BookingDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return convertToDTO(booking);
    }


    public BookingDTO approveBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(Booking.BookingStatus.APPROVED);


        Car car = booking.getCar();
        car.setStatus(Car.CarStatus.RENTED);
        carRepository.save(car);

        Booking updated = bookingRepository.save(booking);
        return convertToDTO(updated);
    }


    public BookingDTO rejectBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(Booking.BookingStatus.REJECTED);
        Booking updated = bookingRepository.save(booking);
        return convertToDTO(updated);
    }


    public BookingDTO cancelBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getStatus().equals(Booking.BookingStatus.PENDING)) {
            throw new RuntimeException("Can only cancel pending bookings");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        return convertToDTO(updated);
    }


    public BookingDTO completeBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(Booking.BookingStatus.COMPLETED);


        Car car = booking.getCar();
        car.setStatus(Car.CarStatus.AVAILABLE);
        carRepository.save(car);

        Booking updated = bookingRepository.save(booking);
        return convertToDTO(updated);
    }


    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                booking.getCar().getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getStatus().toString(),
                booking.getTotalAmount()
        );
    }
}