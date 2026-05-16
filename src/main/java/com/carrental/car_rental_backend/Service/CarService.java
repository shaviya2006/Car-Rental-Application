package com.carrental.car_rental_backend.Service;


import com.carrental.car_rental_backend.dto.CarDTO;
import com.carrental.car_rental_backend.entity.Car;
import com.carrental.car_rental_backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;


    public List<CarDTO> getAvailableCars() {
        return carRepository.findByStatus(Car.CarStatus.AVAILABLE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<CarDTO> searchByBrand(String brand) {
        return carRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public CarDTO getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        return convertToDTO(car);
    }


    public List<CarDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public CarDTO addCar(Car car) {
        car.setStatus(Car.CarStatus.AVAILABLE);
        Car saved = carRepository.save(car);
        return convertToDTO(saved);
    }


    public CarDTO updateCar(Integer id, Car carDetails) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setYear(carDetails.getYear());
        car.setDailyPrice(carDetails.getDailyPrice());
        car.setStatus(carDetails.getStatus());

        Car updated = carRepository.save(car);
        return convertToDTO(updated);
    }


    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }


    private CarDTO convertToDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getDailyPrice(),
                car.getFuelType(),
                car.getType(),
                car.getStatus().toString()
        );
    }
}
