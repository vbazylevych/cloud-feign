package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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
    public void addCarGeneratesId() {
        CarInStore carWithAnyId = new CarInStore(new Car(1, "kot", "krot", 100L), new CarInfo());
        CarInStore carWithGeneratedId = new CarInStore(new Car(1, "kot", "krot", 1L), new CarInfo());
        assertEquals(carService.addCarToStore(carWithAnyId), carWithGeneratedId);
    }

    @Test
    public void addCarsAssignsSequentialId() {
        CarInStore first = new CarInStore(new Car(), new CarInfo());
        CarInStore second = new CarInStore(new Car(), new CarInfo());
        CarInStore firstCarInStore = carService.addCarToStore(first);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carService.addCarToStore(second);
        assertEquals(2, secondCarInStore.getCar().getId());
    }

    @Test
    public void severalCarsCanBeStoredInRepository() {

        CarInStore first = new CarInStore(new Car(), new CarInfo(100500, "Lera"));
        CarInStore second = new CarInStore(new Car(), new CarInfo(10000, "Sema"));
        carService.addCarToStore(first);
        carService.addCarToStore(second);

        Collection<CarInStore> allCars = carService.getAllCars();
        assertTrue(allCars.contains(first));
        assertTrue(allCars.contains(second));
        assertThat(allCars.size(), is(2));
    }

    @Test
    public void emptyCarCanBeAdded() {
        CarInStore emptyCarInStore = new CarInStore(new Car(), new CarInfo());
        assertEquals(1, carService.addCarToStore(emptyCarInStore).getCar().getId());
    }

    @Test
    public void theSameCarsCanBeAdded() {
        CarInStore car = new CarInStore(new Car(), new CarInfo(100500, "Lera"));
        CarInStore firstCarInStore = carService.addCarToStore(car);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carService.addCarToStore(car);
        assertEquals(2, secondCarInStore.getCar().getId());
    }
}
