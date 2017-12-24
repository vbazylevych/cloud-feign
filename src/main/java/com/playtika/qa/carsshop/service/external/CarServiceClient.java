package com.playtika.qa.carsshop.service.external;


import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.service.external.CarServceFeignConfigurton;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CarService", url = "http://localhost:8080", configuration = CarServceFeignConfigurton.class)
public interface CarServiceClient {

    @PostMapping(value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    public long createCar(@RequestParam("price") int price,
                          @RequestParam("contact") String contactDetails,
                          @RequestBody Car car);
}
