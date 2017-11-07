package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
public class CarController {

    private Map<AtomicLong, CarInStore> storedCars = new HashMap<>();
    private AtomicLong id = new AtomicLong(0);

    @PostMapping(value = "/cars",produces = MediaType.APPLICATION_JSON_VALUE)
    public AtomicLong createCar(@RequestParam("price") int price,
                                @RequestParam("contact") String contactDetails,
                                @RequestBody Car car) {
        id.addAndGet(1);
        CarInStore carInStore = new CarInStore(car, price, contactDetails);
        storedCars.put(id, carInStore);
        log.info("Car with id {} was successfully created", id);
        return id;
    }


    @RequestMapping(value = "/cars/{wantedId}" , method = RequestMethod.GET)
    public Car getCar(@PathVariable(value="wantedId") long wantedId) {
        Car wantedCar = storedCars.get(id).getCar();
        log.info("Car with id {} was successfully founded", wantedId);
        return wantedCar;
    }


  /*  @GetMapping
    public ResponseEntity getAllCars() {
        log.info("List of cars will be returned");
        return new ResponseEntity(storedCars, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCars(@PathVariable("id") int carId) {
        if (storedCars.remove(carId) != null) {
            log.info("Car {} was deleted", carId);
            return new ResponseEntity("Successfully deleted", OK);
        } else {
            return new ResponseEntity("No such car", NOT_FOUND);
        }
    }

    private void setDefaultResponseHeader() {
        responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

   }   */
}