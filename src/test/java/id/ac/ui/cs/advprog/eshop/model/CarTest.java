package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("123e4567-e89b-12d3-a456-426614174000");
        car.setCarName("Test Car");
        car.setCarColor("Red");
        car.setCarQuantity(50);
    }

    @Test
    void testGetCarId() {
        assertEquals("123e4567-e89b-12d3-a456-426614174000", car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Test Car", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Red", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(50, car.getCarQuantity());
    }
}
