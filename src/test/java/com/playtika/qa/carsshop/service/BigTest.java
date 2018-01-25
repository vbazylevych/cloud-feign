package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.User;
import com.playtika.qa.carsshop.service.external.CarServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
public class BigTest {

    @Autowired
    CarServiceClient carServiceClient;
    @Autowired
    RegistrationService registrationService;


    @Test
    public void addCar() throws Exception {
        User user = new User ("kot", "krot","064545678");
        registrationService.processFileAndRegisterCars("src/test/resources/test.csv");
        carServiceClient.getAllCars();
        carServiceClient.createDeal(100, 1,user);



    }

}
