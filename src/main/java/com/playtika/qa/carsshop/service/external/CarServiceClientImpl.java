package com.playtika.qa.carsshop.service.external;

import com.playtika.qa.carsshop.domain.Car;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CarServiceClientImpl implements CarServiceClient {

    CarServiceClient carServiceClient;

    @Override
    public long createCar(int price, String contactDetails, Car car) {
        long carId = -1;
        try {
            carId = carServiceClient.createCar(price, contactDetails, car);
        } catch (feign.FeignException e) {
            log.error("vse ploxo" + e.getLocalizedMessage());
        }
        return carId;
    }
}
