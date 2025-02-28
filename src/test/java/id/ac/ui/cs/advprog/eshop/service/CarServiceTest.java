package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Spy
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        // Since CarRepository uses an in-memory list, every test starts with a fresh repository.
    }

    @Test
    public void testEditCar_Positive() {
        // Create and save
        Car car = new Car();
        car.setCarId(null);
        car.setCarName("Car A");
        car.setCarColor("Red");
        car.setCarQuantity(10);
        Car createdCar = carService.create(car);

        // Update using the new method
        createdCar.setCarName("Car A Updated");
        createdCar.setCarColor("Blue");
        createdCar.setCarQuantity(20);
        Car updatedCar = carService.updateCarAndReturn(createdCar.getCarId(), createdCar);

        assertNotNull(updatedCar);
        assertEquals("Car A Updated", updatedCar.getCarName());
        assertEquals("Blue", updatedCar.getCarColor());
        assertEquals(20, updatedCar.getCarQuantity());
    }


    @Test
    public void testEditCar_Negative() {
        // Try updating a Car with an id that does not exist in the repository
        Car car = new Car();
        car.setCarId("9999"); // non-existent id
        car.setCarName("Non Existent Car");
        car.setCarColor("Green");
        car.setCarQuantity(5);
        Car updatedCar = carService.updateCarAndReturn(car.getCarId(), car);

        // The update should return null because the car does not exist
        assertNull(updatedCar, "Update should return null for a non-existent car");
    }

    @Test
    public void testDeleteCar_Positive() {
        // Create a Car and save it to the repository
        Car car = new Car();
        car.setCarId(null);
        car.setCarName("Car B");
        car.setCarColor("Yellow");
        car.setCarQuantity(15);
        Car createdCar = carService.create(car);
        String id = createdCar.getCarId();

        // Delete the existing car
        carService.deleteCarById(id);

        // Verify that the car can no longer be found
        Car foundCar = carService.findById(id);
        assertNull(foundCar, "Car should be deleted and not found");
    }

    @Test
    public void testDeleteCar_Negative() {
        // Try to delete a car with an id that does not exist in the repository.
        String nonExistentId = "9999";
        // The delete operation should not throw an exception
        assertDoesNotThrow(() -> carService.deleteCarById(nonExistentId));

        // Ensure that the repository remains empty if no cars were stored before
        assertTrue(carService.findAll().isEmpty(), "Repository should remain empty after attempting to delete a non-existent car");
    }
    @Test
    public void testUpdateCar_Positive() {
        // Arrange: create and save a new car
        Car car = new Car();
        car.setCarId(null);
        car.setCarName("Car X");
        car.setCarColor("Red");
        car.setCarQuantity(10);
        Car createdCar = carService.create(car);

        // Act: modify properties and update using the update method
        createdCar.setCarName("Car X Updated");
        createdCar.setCarColor("Blue");
        createdCar.setCarQuantity(20);
        Car updatedCar = carService.update(createdCar.getCarId(), createdCar);

        // Assert: verify that the update was successful
        assertNotNull(updatedCar);
        assertEquals("Car X Updated", updatedCar.getCarName());
        assertEquals("Blue", updatedCar.getCarColor());
        assertEquals(20, updatedCar.getCarQuantity());
    }

    @Test
    public void testUpdateCar_Negative() {
        // Arrange: prepare a car with a non-existent id
        Car car = new Car();
        car.setCarId("nonexistent-id");
        car.setCarName("Fake Car");
        car.setCarColor("Grey");
        car.setCarQuantity(0);

        // Act: try updating a car that does not exist
        Car updatedCar = carService.update("nonexistent-id", car);

        // Assert: verify that the update returns null
        assertNull(updatedCar, "Update should return null for a non-existent car");
    }

}
