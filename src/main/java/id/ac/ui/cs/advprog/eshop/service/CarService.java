package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.List;

public interface CarService {
    Car create(Car car);

    List<Car> findAll();

    Car findById(String carId);

    Car update(String carId, Car car);

    Car updateCarAndReturn(String carId, Car car);  // <-- NEW method

    void deleteCarById(String id);
}