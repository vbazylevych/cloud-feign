package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class CarServiceImplAddCarTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void addCarReturnsCarWithGeneratedId() {
        CarInStore carWithAnyId = new CarInStore(new Car(1, "kot", "krot", 100L), 100500, "Lera");
        CarInStore carWithGeneratedId = new CarInStore(new Car(1, "kot", "krot", 1L), 100500, "Lera");
        assertEquals(carService.addCarToStore(carWithAnyId), carWithGeneratedId);
    }

    @Test
    public void addCarsAssignsSequentialId() {
        CarInStore first = new CarInStore(new Car(), 100500, "Lera");
        CarInStore second = new CarInStore(new Car(), 10000, "Sema");
        CarInStore firstCarInStore = carService.addCarToStore(first);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carService.addCarToStore(second);
        assertEquals(2, secondCarInStore.getCar().getId());
    }

    @Test
    public void addCarStoresDataInRepository() {

        CarInStore first = new CarInStore(new Car(), 100500, "Lera");
        CarInStore second = new CarInStore(new Car(), 10000, "Sema");
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        assertTrue( carService.getAllCars().contains(first));
        assertTrue (carService.getAllCars().contains(second));
        assertThat(carService.getAllCars().size(),is(2));
    }

    @Test
    public void emptyCarCanBeAdded() {
        CarInStore emptyCarInStore = new CarInStore(new Car(), 0, "");
        assertEquals(1, carService.addCarToStore(emptyCarInStore).getCar().getId());
    }
    @Test
    public void theSameCarsCanBeAdded() {
        CarInStore car = new CarInStore(new Car(), 100500, "Lera");
        CarInStore firstCarInStore = carService.addCarToStore(car);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carService.addCarToStore(car);
        assertEquals(2, secondCarInStore.getCar().getId());
    }
}
