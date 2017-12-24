package com.playtika.qa.carsshop.service.external;

import com.playtika.qa.carsshop.domain.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarServiceClientImpl implements CarServiceClient {

    CarServiceClient carServiceClient;

    @Override
    public long createCar(int price, String contactDetails, Car car) {
        try {
            return carServiceClient.createCar(price, contactDetails, car);
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }
}
