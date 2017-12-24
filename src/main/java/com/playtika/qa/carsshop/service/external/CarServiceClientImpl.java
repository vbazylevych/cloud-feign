package com.playtika.qa.carsshop.service.external;

import com.playtika.qa.carsshop.domain.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceClientImpl implements CarServiceClient {

    CarServiceClient carServiceClient;

    @Override
    public long createCar(int price, String contactDetails, Car car) {
        long carId = -1;
        try {
            carId = carServiceClient.createCar(price, contactDetails, car);
        } catch (feign.FeignException e) {
            if (e.getLocalizedMessage().contains("Car already selling!")) {
                throw new AlreadyReportedException("Car with plat number " + car.getPlateNumber() + " already selling!");
            }
        }
        return carId;
    }
}
