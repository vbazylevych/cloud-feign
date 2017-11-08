package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@RestController
public class CarController {

    private final Map<Long, CarInStore> storedCars = new HashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @PostMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public AtomicLong createCar(@RequestParam("price") int price,
                                @RequestParam("contact") String contactDetails,
                                @RequestBody Car car) {

        CarInStore carInStore = new CarInStore(car, price, contactDetails);
        storedCars.put(id.addAndGet(1), carInStore);
        log.info("Car with id {} was successfully created", id);
        return id;
    }

    @GetMapping(value = "/cars/{id}")
    public Map<String, Object> getCar(@PathVariable(value = "id") long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("price:", storedCars.get(id).getPrice());
            response.put("contact:", storedCars.get(id).getContact());
            log.info("Car with id {} was successfully founded", id);
        } catch (Exception e) {
            log.info("Can't find car with id {}", id);
        }
        return response;
    }

    @GetMapping("/cars")
    public Collection<CarInStore> getAllCars() {
        log.info("List of cars will be returned");
        return storedCars.values();
    }

    @DeleteMapping("cars/{id}")
    public void deleteCars(@PathVariable("id") long id) {

        if (storedCars.containsKey(id)) {
            storedCars.remove(id);
            log.info("Car {} was deleted", id);
        } else {
            log.warn("Cant delete car with id {}", id);
        }
    }
}