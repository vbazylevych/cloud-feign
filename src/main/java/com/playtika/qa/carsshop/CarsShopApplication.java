package com.playtika.qa.carsshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CarsShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsShopApplication.class, args);
    }
}
