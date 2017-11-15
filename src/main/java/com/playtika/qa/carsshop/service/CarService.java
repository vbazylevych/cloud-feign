package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;

import java.util.Collection;
import java.util.Map;

public interface CarService {
    CarInStore addCarToStore(CarInStore carInStore);

    CarInfo getCar(long id);

    Collection<CarInStore> getAllCars();

    void deleteCar(long id);


}
