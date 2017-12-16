package com.playtika.qa.carsshop.service.external.http;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.playtika.qa.carsshop.service.external.domain.CarRegisterRequest;
import com.playtika.qa.carsshop.service.external.http.CarServiceClient;
import com.playtika.qa.carsshop.service.external.http.CarServiceClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CarServiceClientImplTest {


    @Autowired
    private CarServiceClient service;

    @Rule
    public WireMockRule wm = new WireMockRule(options().port(8090));

    @Test
    public void name() throws Exception {
        CarRegisterRequest newcar = CarRegisterRequest.builder()
                .color("red")
                .model("opel")
                .plateNumber("sfffd")
                .year(2010)
                .build();

        stubFor(post("/cars/?price=2&contact=2")
                .willReturn(ok("1")));

        long rrr = service.createCar(2, "2", newcar);
assert (rrr>0);
    }
}

