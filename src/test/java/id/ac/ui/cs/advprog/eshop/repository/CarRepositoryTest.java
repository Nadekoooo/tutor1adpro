package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        // Reinitialize the repository to ensure isolation between tests.
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("123e4567-e89b-12d3-a456-426614174000");
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(5);
        carRepository.createCar(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarId("123e4567-e89b-12d3-a456-426614174000");
        car1.setCarName("Test Car One");
        car1.setCarColor("Red");
        car1.setCarQuantity(10);
        carRepository.createCar(car1);

        Car car2 = new Car();
        car2.setCarId("223e4567-e89b-12d3-a456-426614174001");
        car2.setCarName("Test Car Two");
        car2.setCarColor("Green");
        car2.setCarQuantity(20);
        carRepository.createCar(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar1 = carIterator.next();
        assertEquals(car1.getCarId(), savedCar1.getCarId());

        Car savedCar2 = carIterator.next();
        assertEquals(car2.getCarId(), savedCar2.getCarId());

        assertFalse(carIterator.hasNext());
    }

    @Test
    void testEditCar() {
        // Create the first car
        Car firstCar = new Car();
        firstCar.setCarId("car-1");
        firstCar.setCarName("Car One");
        firstCar.setCarColor("Blue");
        firstCar.setCarQuantity(5);
        carRepository.createCar(firstCar);

        // Create the second car
        Car secondCar = new Car();
        secondCar.setCarId("car-2");
        secondCar.setCarName("Car Two");
        secondCar.setCarColor("Red");
        secondCar.setCarQuantity(10);
        carRepository.createCar(secondCar);

        // Now update the second car (forcing the loop to skip the first car)
        secondCar.setCarName("Car Two Updated");
        secondCar.setCarColor("Yellow");
        secondCar.setCarQuantity(20);
        Car updatedCar = carRepository.update(secondCar.getCarId(), secondCar);

        // Verify that the second car got updated
        assertNotNull(updatedCar);
        assertEquals("Car Two Updated", updatedCar.getCarName());
        assertEquals("Yellow", updatedCar.getCarColor());
        assertEquals(20, updatedCar.getCarQuantity());

        // Verify that the first car is still unchanged
        Car unchangedCar = carRepository.findById("car-1");
        assertEquals("Car One", unchangedCar.getCarName());
        assertEquals("Blue", unchangedCar.getCarColor());
        assertEquals(5, unchangedCar.getCarQuantity());
    }


    @Test
    void testEditCar_NonExistent() {
        Car car = new Car();
        car.setCarId("nonexistent-id");
        car.setCarName("Nonexistent Car");
        car.setCarColor("Gray");
        car.setCarQuantity(0);

        // update should return null when car is not found
        Car result = carRepository.update(car.getCarId(), car);
        assertNull(result);

        // Verify that repository remains empty
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setCarId("123e4567-e89b-12d3-a456-426614174000");
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(5);
        carRepository.createCar(car);

        // Delete the car
        carRepository.delete(car.getCarId());

        // Verify the car is deleted
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDeleteCar_NonExistent() {
        // Attempt to delete a non-existent car
        carRepository.delete("nonexistent-id");

        // Repository should still be empty
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindById_NegativeCase() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Test Car");
        car.setCarColor("Yellow");
        car.setCarQuantity(7);
        carRepository.createCar(car);

        // Try to find a car with a different id
        Car result = carRepository.findById("2");
        assertNull(result, "Expected findById to return null when no car with the given id exists");
    }
}
