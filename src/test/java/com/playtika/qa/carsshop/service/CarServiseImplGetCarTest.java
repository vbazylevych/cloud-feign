package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.Assert.assertEquals;

public class CarServiseImplGetCarTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
        Map<Long, CarInStore> expectedRepository = new ConcurrentHashMap<>();
        CarInStore first = new CarInStore(new Car(), 1, "Lera");
        CarInStore second = new CarInStore(new Car(), 2, "Sema");
        CarInStore third = new CarInStore(new Car(), 0, "");
        expectedRepository.put(1L, first);
        expectedRepository.put(2L, second);
        expectedRepository.put(3L, third);
        carService.setStoredCars(expectedRepository);
    }

    @Test
    public void getCarReturnsAppropriateCar() {

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 2);
        expectedResponse.put("contact", "Sema");
        assertEquals(expectedResponse, carService.getCar(2L));
    }

    @Test
    public void getCarReturnsFirstCar() {
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 1);
        expectedResponse.put("contact", "Lera");
        assertEquals(expectedResponse, carService.getCar(1L));
    }

    @Test
    public void getCarReturnsLastCar() {
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("price", 0);
        expectedResponse.put("contact", "");
        assertEquals(expectedResponse, carService.getCar(3L));
    }

    @Test
    public void getCarReturnsEmptyCarIfIdMoreThanExist() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(100500L));
    }
    @Test
    public void getCarReturnsEmptyCarIfIdIsNegative() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(-1L));
    }
    @Test
    public void getCarReturnsEmptyCarIfIdIsZero() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(0L));
    }
    @Test
    public void getCarReturnsEmptyCarIfRepositoryIsEmpty() {
        Map<Long, CarInStore> saveRepository = carService.getStoredCars();
        carService.setStoredCars(new HashMap<>());
        Map<String, Object> expectedResponse = new HashMap<>();
        assertEquals(expectedResponse, carService.getCar(1L));
    }
}

