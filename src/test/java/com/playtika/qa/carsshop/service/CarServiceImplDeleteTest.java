package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CarServiceImplDeleteTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void deleteWhenRepositoryIsEmpty() {
        carService.deleteCar(3);
        assertTrue(carService.getAllCars().isEmpty());
    }

    @Test
    public void deleteWhenRepositoryHasOneItem() {
        carService.addCarToStore(new CarInStore(new Car(), new CarInfo()));
        carService.deleteCar(1);
        assertTrue(carService.getAllCars().isEmpty());
    }

    @Test
    public void deleteWhenRepositoryHasSeveralItems() {
        CarInStore first = new CarInStore(new Car(),new CarInfo(1, "kot"));
        CarInStore second = new CarInStore(new Car(), new CarInfo(2, "krot"));
        CarInStore third = new CarInStore(new Car(), new CarInfo(3, "begemot"));
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        carService.addCarToStore(third);
        carService.deleteCar(2);
        assertTrue(carService.getAllCars().contains(first));
        assertTrue(carService.getAllCars().contains(third));
        assertFalse(carService.getAllCars().contains(second));
        assertThat(carService.getAllCars().size(), is(2));
    }

    @Test
    public void deleteNotExistingItem() {
        CarInStore first = new CarInStore(new Car(),new CarInfo());
        carService.addCarToStore(first);
        carService.deleteCar(20);
        assertTrue(carService.getAllCars().contains(first));
        assertThat(carService.getAllCars().size(), is(1));
    }
}
