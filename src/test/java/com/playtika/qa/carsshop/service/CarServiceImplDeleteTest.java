package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;


public class CarServiceImplDeleteTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void deleteWhenRepositoryIsEmpty() {

        Map<String, Object> expectedResponse = new HashMap<>();
        carService.deleteCar(3L);
        assertEquals(carService.getStoredCars(), expectedResponse);
    }
    @Test
    public void deleteWhenRepositoryHasOneItem() {
        Map<String, Object> expectedResponse = new HashMap<>();
        Map<Long, CarInStore> repository = new HashMap<>();
        repository.put(1L, new CarInStore());
        carService.setStoredCars(repository);
        carService.deleteCar(1L);
        assertEquals(carService.getStoredCars(), expectedResponse);
    }

    @Test
    public void deleteWhenRepositoryHasSeveralItems() {
        Map<Long, CarInStore> repository = new HashMap<>();
        repository.put(1L, new CarInStore());
        repository.put(2L, new CarInStore());
        carService.setStoredCars(repository);
        carService.deleteCar(2L);
        Map<Long, CarInStore> expectedResponse = new HashMap<>();
        expectedResponse.put(1L, new CarInStore());
        assertEquals(carService.getStoredCars(), expectedResponse);
    }

    @Test
    public void deleteNotExistingItem() {
        Map<Long, CarInStore> repository = new HashMap<>();
        repository.put(1L, new CarInStore());
        carService.setStoredCars(repository);
        carService.deleteCar(2L);
        Map<Long, CarInStore> expectedResponse = new HashMap<>();
        expectedResponse.put(1L, new CarInStore());
        assertEquals(carService.getStoredCars(), expectedResponse);
    }
}
