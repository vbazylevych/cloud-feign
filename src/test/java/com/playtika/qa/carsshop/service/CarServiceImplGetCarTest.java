package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

public class CarServiceImplGetCarTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
        CarInStore first = new CarInStore(new Car(), 1, "Lera");
        CarInStore second = new CarInStore(new Car(), 2, "Sema");
        CarInStore third = new CarInStore(new Car(), 0, "");
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        carService.addCarToStore(third);
    }

    @Test
    public void getCarReturnsAppropriateCar() {

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 2);
        expectedResponse.put("contact", "Sema");
        assertEquals(expectedResponse, carService.getCar(2));
    }

    @Test
    public void getCarReturnsFirstCar() {
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 1);
        expectedResponse.put("contact", "Lera");
        assertEquals(expectedResponse, carService.getCar(1));
    }

    @Test
    public void getCarReturnsLastCar() {
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 0);
        expectedResponse.put("contact", "");
        assertEquals(expectedResponse, carService.getCar(3));
    }

    @Test
    public void getCarReturnsEmptyCarIfIdMoreThanExist() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(100500));
    }

    @Test
    public void getCarReturnsEmptyCarIfIdIsNegative() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(-1));
    }

    @Test
    public void getCarReturnsEmptyCarIfIdIsZero() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(0));
    }

    @Test
    public void getCarReturnsEmptyCarIfRepositoryIsEmpty() {
       carService.deleteCar(1);
       carService.deleteCar(2);
       carService.deleteCar(3);
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(1));
    }
}

