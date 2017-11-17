package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import com.playtika.qa.carsshop.web.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;


public class ServiceUnitTests {
    private CarService carService = new CarServiceImpl();

    @Test
    public void addCarsAssignsSequentialId() {
        CarInStore first = new CarInStore(new Car(), new CarInfo(1, ""));
        CarInStore second = new CarInStore(new Car(), new CarInfo(1,""));
        CarInStore firstCarInStore = carService.addCarToStore(first);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carService.addCarToStore(second);
        assertEquals(2, secondCarInStore.getCar().getId());
    }

    @Test
    public void allCarsReturnsCars() {
        CarInStore first = new CarInStore(new Car(), new CarInfo(1, "Lera"));
        CarInStore second = new CarInStore(new Car(), new CarInfo(2, "kot"));
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        Collection<CarInStore> allCars = carService.getAllCars();
        assertTrue(allCars.contains(first));
        assertTrue(allCars.contains(second));
        assertThat(allCars.size(), is(2));
    }

    @Test
    public void allCarsReturnsEmptyResponseIfRepositoryIsEmpty() {
        assertThat(carService.getAllCars(), is(empty()));
    }

    @Test
    public void getCarReturnsAppropriateCar() {
        CarInfo expectedResponse = new CarInfo(2, "Sema");
        CarInStore carInStore = new CarInStore(new Car(), expectedResponse);
        CarInStore carInStoreWrong = new CarInStore(new Car(), new CarInfo(10, "kot"));
        carService.addCarToStore(carInStore);
        assertEquals(Optional.of(expectedResponse), carService.getCar(1));
    }

    @Test
    public void getCarReturnsEmptyResponseIfCarIsAbsent() {
        assertFalse(carService.getCar(-10).isPresent());
    }

    @Test
    public void deleteWhenRepositoryIsEmpty() {
        assertFalse(carService.deleteCar(3));
        assertTrue(carService.getAllCars().isEmpty());
    }

    @Test
    public void deleteWhenRepositoryHasSeveralItems() {
        CarInStore first = new CarInStore(new Car(), new CarInfo(1, "kot"));
        CarInStore second = new CarInStore(new Car(), new CarInfo(2, "krot"));
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        assertTrue(carService.deleteCar(2));
        assertTrue(carService.getAllCars().contains(first));
        assertFalse(carService.getAllCars().contains(second));
        assertThat(carService.getAllCars().size(), is(1));
    }
}

