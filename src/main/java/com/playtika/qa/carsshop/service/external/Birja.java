package com.playtika.qa.carsshop.service.external;

import com.playtika.qa.carsshop.service.external.domain.CarRegisterRequest;
import com.playtika.qa.carsshop.service.external.http.CarServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Birja {
    @Autowired
    CarServiceClient carServiceClient;

    public void register() {
        CarRegisterRequest car = CarRegisterRequest.builder()
                .model("opel")
                .color("red")
                .year(2000)
                .plateNumber("ggg").build();
        carServiceClient.createCar(10, "", car);
    }
}
