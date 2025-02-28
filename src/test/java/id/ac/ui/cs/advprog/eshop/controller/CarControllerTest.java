package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCarPage() {
        String view = carController.createCarPage(model);
        // Verify that a new Car object is added to the model under the attribute "car"
        verify(model).addAttribute(eq("car"), any(Car.class));
        assertEquals("CreateCar", view);
    }

    @Test
    public void testCreateCarPost() {
        Car car = new Car();
        String view = carController.createCarPost(car, model);
        // Verify that the service creates the car
        verify(carService).create(car);
        assertEquals("redirect:listCar", view);
    }

    @Test
    public void testCarListPage() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        when(carService.findAll()).thenReturn(cars);
        String view = carController.carListPage(model);
        // Verify that the list of cars is added to the model under the attribute "cars"
        verify(model).addAttribute("cars", cars);
        assertEquals("CarList", view);
    }

    @Test
    public void testEditCarPage() {
        String carId = "123";
        Car car = new Car();
        when(carService.findById(carId)).thenReturn(car);
        String view = carController.editCarPage(carId, model);
        // Verify that the correct car is added to the model under the attribute "car"
        verify(model).addAttribute("car", car);
        assertEquals("EditCar", view);
    }

    @Test
    public void testEditCarPost() {
        Car car = new Car();
        // Assume Car has a setter for carId
        car.setCarId("456");
        String view = carController.editCarPost(car, model);
        // Verify that the service updates the car using the provided carId
        verify(carService).update("456", car);
        assertEquals("redirect:listCar", view);
    }

    @Test
    public void testDeleteCar() {
        String carId = "789";
        String view = carController.deleteCar(carId);
        // Verify that the service deletes the car by its carId
        verify(carService).deleteCarById(carId);
        assertEquals("redirect:listCar", view);
    }
}
