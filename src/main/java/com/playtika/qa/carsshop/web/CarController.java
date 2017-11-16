package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import com.playtika.qa.carsshop.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@RestController
@AllArgsConstructor
public class CarController {

    private final CarService service;


    @PostMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public long createCar(@RequestParam("price") int price,
                          @RequestParam("contact") String contactDetails,
                          @RequestBody Car car) {
        log.info("Create new car request was received");

        CarInStore carInStore = new CarInStore(car, new CarInfo(price, contactDetails));
        CarInStore newCarInStore = service.addCarToStore(carInStore);
        return newCarInStore.getCar().getId();
    }

    @GetMapping(value = "/cars/{id}")
    public Optional<CarInfo> getCar(@PathVariable(value = "id") long id) throws NotFoundException {
        log.info("get request with id {} received", id);
        Optional<CarInfo> carInfo = service.getCar(id);
        if (!carInfo.isPresent()) {
            throw new NotFoundException("Can't find car");
        } else {
            return carInfo;
        }
    }

    @GetMapping("/cars")
    public Collection<CarInStore> getAllCars() {
        log.info("Get all cars request was received");
        return service.getAllCars();
    }

    @DeleteMapping("cars/{id}")
    public void deleteCars(@PathVariable("id") long id) {
        service.deleteCar(id);
    }
}