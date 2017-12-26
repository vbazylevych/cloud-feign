package com.playtika.qa.carsshop.service.external;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.service.external.exception.BadRequestException;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadyOnSaleException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CarServiceClientTest {

    @Autowired
    private CarServiceClient service;

    @Rule
    public WireMockRule wm = new WireMockRule(options().port(8080));
    Car car = Car.builder()
            .color("red")
            .model("opel")
            .plateNumber("xxx")
            .year(2010)
            .build();

    @Test
    public void registration_successful() {
        String jsonCar = "{\"plateNumber\": \"xxx\", \"color\": \"red\", \"model\": \"opel\", \"year\": 2010 } ";
        stubFor(post("/cars?price=2&contact=2")
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson(jsonCar))
                .willReturn(ok("1")));
        assertThat(service.createCar(2, "2", car), is(1L));
    }

    @Test(expected = CarAlreadyOnSaleException.class)
    public void registration_whenCarAlreadyExist_returnsCorrectErrorCode() {
        stubFor(post("/cars?price=2&contact=2")
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse().withStatus(302)));
        service.createCar(2, "2", car);
    }

    @Test(expected = BadRequestException.class)
    public void registration_withBadRequest_returnsCorrectErrorCode() {
        stubFor(post("/cars?price=2&contact=2")
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse().withStatus(400)));
        service.createCar(2, "2", car);
    }
}

