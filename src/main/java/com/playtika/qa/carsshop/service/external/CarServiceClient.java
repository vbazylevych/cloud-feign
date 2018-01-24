package com.playtika.qa.carsshop.service.external;


import com.playtika.qa.carsshop.domain.*;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "CarService", configuration = CarServceFeignConfiguration.class)
public interface CarServiceClient {

    @PostMapping(value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    long createCar(@RequestParam("price") int price,
                   @RequestParam("contact") String contactDetails,
                   @RequestBody Car car);

    @GetMapping(value = "/cars/{id}", produces = APPLICATION_JSON_VALUE)
    CarInfo getCar(@PathVariable(value = "id") long id);

    @GetMapping(value = "/cars", produces = APPLICATION_JSON_VALUE)
    Collection<CarInStore> getAllCars();

    @DeleteMapping("cars/{id}")
    void deleteCars(@PathVariable("id") long id);

    @PostMapping(value = "/deal", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    long createDeal(@RequestParam("price") int price,
                    @RequestParam("adsId") long adsId,
                    @RequestBody User user);

    @PostMapping(value = "/deal/reject/{id}", produces = APPLICATION_JSON_VALUE)
    void rejectDeal(@PathVariable(value = "id") long id);

    @PostMapping(value = "/deal/accept/{id}", produces = APPLICATION_JSON_VALUE)
    BestDealResponse acceptBestDeal(@PathVariable(value = "id") long id);
}
