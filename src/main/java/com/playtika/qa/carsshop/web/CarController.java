package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);
    private List<Car> storedCars = new ArrayList<>();
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
            setDefoultResponseHeader();
            LOG.info("Car with id {} was succesfully founded", id);
            return new ResponseEntity<>(response.toString(), responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.error("Cant get cap with id {}", id);
            setDefoultResponseHeader();
            return new ResponseEntity<>("{}", responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcars")
    public ResponseEntity getStoredCars() {
        LOG.info("List of cars will be returned");
        return new ResponseEntity(storedCars, HttpStatus.OK);
    }

    @PostMapping(value = "/addcar")
    public ResponseEntity createCustomer(@RequestParam("price") int price,
                                         @RequestParam("contact") String contactDetails,
                                         @RequestBody Map<String, String> json) throws JSONException {
        Car newCar = new Car(price, contactDetails, json);
        LOG.debug("New Car with parameters {} was created", newCar.toString());

        storedCars.add(newCar);
        int carId = storedCars.indexOf(newCar);
        LOG.debug("New Car with parameters {} was stored", newCar.toString());
        LOG.info("Car with id {} was succesfully created", carId);

        JSONObject response = new JSONObject();
        response.put("carId", carId);
        LOG.debug("Response {} was send to client", response.toString());
        setDefoultResponseHeader();
        return new ResponseEntity(response.toString(), responseHeaders, HttpStatus.OK);

    }
    private void setDefoultResponseHeader() {
        responseHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }
}
