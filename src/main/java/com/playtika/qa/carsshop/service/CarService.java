package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import java.util.Optional;
import java.util.Collection;


public interface CarService {
    CarInStore addCarToStore(CarInStore carInStore);

    Optional<CarInfo>getCar(long id);

    Collection<CarInStore> getAllCars();

    Boolean deleteCar(long id);


}
