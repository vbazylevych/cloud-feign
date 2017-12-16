package com.playtika.qa.carsshop.service.external.http;

import com.playtika.qa.carsshop.CarsShopApplication;
import com.playtika.qa.carsshop.service.external.domain.CarRegisterRequest;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

@Slf4j
@AllArgsConstructor
public class CarServiceClientImpl implements CarServiceClient {
    CarServiceClient feignClient;

    @Override
    public long createCar(int price, String contactDetails, CarRegisterRequest car) {
        log.info("car was created with");
        return feignClient.createCar(price, contactDetails, car);
    }
}
