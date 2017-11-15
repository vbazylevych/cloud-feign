package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CarServiseImplGetCarsTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void emptyResponseWhenRepositoryIsEmpty() {
        assertThat(carService.getAllCars(), is(empty()));
    }

    @Test
    public void oneCarCanBeReturned() {
        CarInStore first = new CarInStore(new Car(), new CarInfo( 1, "Lera"));
        carService.addCarToStore(first);
        assertTrue(carService.getAllCars().contains(first));
        assertThat(carService.getAllCars().size(), is(1));
    }

 //@Test several cars can be returned was tested in CarServiceImplAddCarTest.addCarStoresDataInRepository()

}
