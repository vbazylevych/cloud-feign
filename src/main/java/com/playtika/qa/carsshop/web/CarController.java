package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Controller
public class CarController {

    private Map<Integer, Car> storedCars = new HashMap<>();
    private int id = 1;
    private HttpHeaders responseHeaders = new HttpHeaders();

    @SneakyThrows
    @GetMapping(value = "/getcar")
    public ResponseEntity getSpecificCar(@RequestParam("id") int id) {
        setDefaultResponseHeader();
        try {
            Car wantedCar = storedCars.get(id);
            log.debug("Car with id {} was found", id);

            JSONObject response = new JSONObject();
            response.put("price", wantedCar.getPrice());
            log.debug("Get price for {} car", id);
            response.put("contact", wantedCar.getContactDetails());
            log.debug("Get contact for {} car", id);
            log.info("Car with id {} was successfully founded", id);
            return new ResponseEntity<>(response.toString(), responseHeaders, CREATED);
        } catch (Exception e) {
            log.error("Cant get car with id {}", id);
            return new ResponseEntity<>("{}", responseHeaders, NOT_FOUND);
        }
    }

    @GetMapping("/getcars")
    public ResponseEntity getStoredCars() {
        log.info("List of cars will be returned");
        return new ResponseEntity(storedCars, OK);
    }

    @PostMapping(value = "/addcar")
    public ResponseEntity createCustomer(@RequestParam("price") int price,
                                         @RequestParam("contact") String contactDetails,
                                         @RequestBody Map<String, String> json) throws JSONException {
        Car newCar = new Car(price, contactDetails, json);
        log.debug("New Car with parameters {} was created", newCar.toString());

        storedCars.put(id, newCar);
        log.info("Car with id {} was successfully created", id);

        JSONObject response = new JSONObject();
        response.put("carId", id);
        id = ++id;
        log.debug("Response {} was send to client", response.toString());
        setDefaultResponseHeader();
        return new ResponseEntity(response.toString(), responseHeaders, CREATED);
    }

    @DeleteMapping("/delcar/{id}")
    public ResponseEntity getStoredCars(@PathVariable("id") int carId) {
        if (storedCars.remove(carId) != null) {
            log.info("Car {} was deleted", carId);
            return new ResponseEntity("Successfully deleted", OK);
        } else {
            return new ResponseEntity("No such car", NOT_FOUND);
        }
    }

    private void setDefaultResponseHeader() {
        responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }
}