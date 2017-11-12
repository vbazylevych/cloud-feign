package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CarServiseImplGetCarsTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void emptyResponseWhenRepositoryIsEmpty() {
        Map<String, Object> expectedResponse = new HashMap<>();
        assertThat(carService.getAllCars(), is(empty()));
    }

    @Test
    public void oneCarCanBeReturned() {
        Map<Long, CarInStore> expectedRepository = new ConcurrentHashMap<>();
        CarInStore first = new CarInStore(new Car(), 1, "Lera");
        expectedRepository.put(1L, first);
        carService.setStoredCars(expectedRepository);

        assertEquals(carService.getAllCars(), expectedRepository.values());
    }
    @Test
    public void severalCarCanBeReturned() {
        Map<Long, CarInStore> expectedRepository = new ConcurrentHashMap<>();
        CarInStore first = new CarInStore(new Car(), 1, "Lera");
        CarInStore second = new CarInStore(new Car(), 2, "Sema");
        expectedRepository.put(1L, first);
        expectedRepository.put(2L, second);
        carService.setStoredCars(expectedRepository);

        assertEquals(carService.getAllCars(), expectedRepository.values());
    }

}
