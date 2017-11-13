package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;

import java.util.Collection;
import java.util.Map;

public interface CarService {
    CarInStore addCarToStore(CarInStore carInStore);

    Map<String, Object> getCar(long id);

    Collection<CarInStore> getAllCars();

    void deleteCar(long id);


}
