package com.carrental.car_rental_backend.Controller;



import com.carrental.car_rental_backend.dto.CarDTO;
import com.carrental.car_rental_backend.entity.Car;
import com.carrental.car_rental_backend.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class CarController {

    private final CarService carService;


    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        List<CarDTO> cars = carService.getAvailableCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestParam String brand) {
        List<CarDTO> cars = carService.searchByBrand(brand);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Integer id) {
        CarDTO car = carService.getCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CarDTO> addCar(@RequestBody Car car) {
        CarDTO newCar = carService.addCar(car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(
            @PathVariable Integer id,
            @RequestBody Car carDetails) {
        CarDTO updated = carService.updateCar(id, carDetails);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return new ResponseEntity<>("Car deleted", HttpStatus.OK);
    }
}
