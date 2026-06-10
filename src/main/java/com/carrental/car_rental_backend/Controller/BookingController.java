package com.carrental.car_rental_backend.Controller;


import com.carrental.car_rental_backend.dto.BookingDTO;
import com.carrental.car_rental_backend.entity.Booking;
import com.carrental.car_rental_backend.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class BookingController {

    private final BookingService bookingService;


    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @RequestParam Integer userId,
            @RequestParam Integer carId,
            @RequestBody Booking booking) {
        BookingDTO newBooking = bookingService.createBooking(userId, carId, booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@PathVariable Integer userId) {
        List<BookingDTO> bookings = bookingService.getUserBookings(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }


    @PutMapping("/{id}/approve")
    public ResponseEntity<BookingDTO> approveBooking(@PathVariable Integer id) {
        BookingDTO booking = bookingService.approveBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }


    @PutMapping("/{id}/reject")
    public ResponseEntity<BookingDTO> rejectBooking(@PathVariable Integer id) {
        BookingDTO booking = bookingService.rejectBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Integer id) {
        BookingDTO booking = bookingService.cancelBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }


    @PutMapping("/{id}/complete")
    public ResponseEntity<BookingDTO> completeBooking(@PathVariable Integer id) {
        BookingDTO booking = bookingService.completeBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}
