package com.playtika.qa.carsshop;

import com.playtika.qa.carsshop.service.external.http.CarServiceClient;
import com.playtika.qa.carsshop.service.external.http.CarServiceClientImpl;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    CarServiceClient carServiceClient() {
        CarServiceClient feignClient = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(CarServiceClient.class, "http://localhost:8090");
        return new CarServiceClientImpl(feignClient);
    }
}
