package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
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
        CarInStore first = new CarInStore(new Car(), new CarInfo(1, "Lera"));
        CarInStore second = new CarInStore(new Car(), new CarInfo( 2, "Sema"));
        CarInStore third = new CarInStore(new Car(), new CarInfo(100500, "kot"));
        carService.addCarToStore(first);
        carService.addCarToStore(second);
        carService.addCarToStore(third);
    }

    @Test
    public void canReturnAppropriateCar() {
        CarInfo expectedResponse = new CarInfo(2, "Sema");
        assertEquals(expectedResponse, carService.getCar(2));
    }

    @Test
    public void canReturnFirstCar() {
        CarInfo expectedResponse = new CarInfo(1, "Lera");
        assertEquals(expectedResponse, carService.getCar(1));
    }

    @Test
    public void canReturnLastCar() {
        CarInfo carInfo = new CarInfo(100500,"kot");
        assertEquals(carInfo, carService.getCar(3));
    }

    @Test
    public void canReturnEmptyCarIfIdMoreThanExist() {
        CarInfo expectedResponse = new CarInfo();
        assertEquals(expectedResponse, carService.getCar(100500));
    }

    @Test
    public void canrReturnEmptyCarIfIdIsNegative() {
        CarInfo expectedResponse = new CarInfo();
        assertEquals(expectedResponse, carService.getCar(-1));
    }

    @Test
    public void canReturnEmptyCarIfIdIsZero() {
       CarInfo expectedResponse = new CarInfo();
        assertEquals(expectedResponse, carService.getCar(0));
    }

    @Test
    public void canReturnEmptyCarIfRepositoryIsEmpty() {
       carService.deleteCar(1);
       carService.deleteCar(2);
       carService.deleteCar(3);
        CarInfo expectedResponse = new CarInfo();
        assertEquals(expectedResponse, carService.getCar(1));
    }
}

