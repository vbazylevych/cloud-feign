package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Controller
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);
    private Map<Integer, Car> storedCars = new HashMap<>();
    private int id = 1;
    private HttpHeaders responseHeaders = new HttpHeaders();


    @GetMapping(value = "/getcar")
    public ResponseEntity getSpecificCar(@RequestParam("id") int id) throws JSONException {

        try {
            Car wantedCar = storedCars.get(id);
            LOG.debug("Car with id {} was founded", id);
            JSONObject response = new JSONObject();
            response.put("price", wantedCar.getPrice());
            LOG.debug("Get price for {} car", id);
            response.put("contact", wantedCar.getContactDetails());
            LOG.debug("Get contact for {} car", id);
            setDefaultResponseHeader();
            LOG.info("Car with id {} was successfully founded", id);
            return new ResponseEntity<>(response.toString(), responseHeaders, CREATED);
        } catch (Exception e) {
            LOG.error("Cant get cap with id {}", id);
            setDefaultResponseHeader();
            return new ResponseEntity<>("{}", responseHeaders, NOT_FOUND);
        }
    }

    @GetMapping("/getcars")
    public ResponseEntity getStoredCars() {
        LOG.info("List of cars will be returned");
        return new ResponseEntity(storedCars, OK);
    }

    @PostMapping(value = "/addcar")
    public ResponseEntity createCustomer(@RequestParam("price") int price,
                                         @RequestParam("contact") String contactDetails,
                                         @RequestBody Map<String, String> json) throws JSONException {
        Car newCar = new Car(price, contactDetails, json);
        LOG.debug("New Car with parameters {} was created", newCar.toString());

        storedCars.put(id, newCar);
        LOG.info("Car with id {} was successfully created", id);

        JSONObject response = new JSONObject();
        response.put("carId", id);
        id = ++id;
        LOG.debug("Response {} was send to client", response.toString());
        setDefaultResponseHeader();
        return new ResponseEntity(response.toString(), responseHeaders, CREATED);

    }

    @DeleteMapping("/delcar/{id}")
    public ResponseEntity getStoredCars(@PathVariable("id") int carId) {
        storedCars.remove(carId);
        LOG.info("Car {} was deleted", carId);
        return new ResponseEntity("Successfully deleted", OK);
    }

    private void setDefaultResponseHeader() {
        responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }
}