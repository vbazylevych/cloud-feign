package com.playtika.qa.carsshop.service.external;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.playtika.qa.carsshop.domain.*;
import com.playtika.qa.carsshop.service.external.exception.BadRequestException;
import com.playtika.qa.carsshop.service.external.exception.CantRejectAcceptedDeal;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadyOnSaleException;
import com.playtika.qa.carsshop.service.external.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
import static org.hamcrest.Matchers.samePropertyValuesAs;


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

    @Test
    public void openDeal_successful() {
        String userString = "{\"name\": \"kot\", \"surname\": \"krot\", \"contact\": \"con1\"}";
        User user = new User("kot", "krot", "con1");
        stubFor(post("/deal?price=100500&adsId=1")
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson(userString))
                .willReturn(ok("1")));
        assertThat(service.createDeal(100500, 1, user), is(1L));
    }

    @Test(expected = NotFoundException.class)
    public void openDeal_NotFoundResponse() {
        String userString = "{\"name\": \"kot\", \"surname\": \"krot\", \"contact\": \"con1\"}";
        User user = new User("kot", "krot", "con1");
        stubFor(post("/deal?price=100500&adsId=1")
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson(userString))
                .willReturn(aResponse().withStatus(404)));
        assertThat(service.createDeal(100500, 1, user), is(1L));
    }

    @Test
    public void rejectDeal_successful() {
        stubFor(post("/deal/reject/1")
                .willReturn(ok()));
        service.rejectDeal(1);
    }

    @Test(expected = NotFoundException.class)
    public void rejectNotExisting_throwsException() {
        stubFor(post("/deal/reject/1")
                .willReturn(aResponse().withStatus(404)));
        service.rejectDeal(1);
    }

    @Test(expected = CantRejectAcceptedDeal.class)
    public void rejectAccepted_throwsException() {
        stubFor(post("/deal/reject/1")
                .willReturn(aResponse().withStatus(406)));
        service.rejectDeal(1);
    }

    @Test
    public void chooseBestDeal_successful() {
        String stringBestDealResponse = "{\"user\":{\"name\":\"kot\",\"surname\":\"krot\",\"contact\":\"con1\"},\"price\":100500,\"id\":1}";
        User user = new User("kot", "krot", "con1");
        BestDealResponse expectedBestDealResponse = new BestDealResponse(user, 100500, 1);
        stubFor(post("/deal/accept/1")
                .willReturn(ok(stringBestDealResponse)));
        BestDealResponse bestDealResponse = service.acceptBestDeal(1);

        Assert.assertThat(bestDealResponse.getUser(), samePropertyValuesAs(expectedBestDealResponse.getUser()));
        Assert.assertThat(bestDealResponse.getPrice(), is(100500));
        Assert.assertThat(bestDealResponse.getId(), is(1L));
    }

    @Test(expected = NotFoundException.class)
    public void chooseBestDeal_adsOrDealAbsent_throwsException() {
        stubFor(post("/deal/accept/1")
                .willReturn(aResponse().withStatus(404)));
        service.acceptBestDeal(1);
    }

    @Test
    public void getCarInfo_successful() {
        CarInfo carInfo = new CarInfo(100500, "con1");
        String response = "{\"price\":100500,\"contact\":\"con1\"}";
        stubFor(get("/cars/1")
                .willReturn(ok(response)));
        assertThat(service.getCar(1), samePropertyValuesAs(carInfo));
    }

    @Test(expected = NotFoundException.class)
    public void getCarInfo_successful_ifCarAbsent() {
        stubFor(get("/cars/1")
                .willReturn(aResponse().withStatus(404)));
        service.getCar(1);
    }

    @Test
    public void getAllOpenAds_successful() {
        String jsonCars = "[{\"car\":{\"id\":1,\"plateNumber\":\"yyy\",\"model\":\"opel\",\"color\":\"red\",\"year\":29017},\"carInfo\":{\"price\":100501,\"contact\":\"con2\"}},{\"car\":{\"id\":2,\"plateNumber\":\"xxx\",\"model\":\"opel\",\"color\":\"red\",\"year\":29017},\"carInfo\":{\"price\":100500,\"contact\":\"con1\"}}] ";
        stubFor(get("/cars")
                .willReturn(ok(jsonCars)));
        service.getAllCars().toArray();
        assertThat(service.getAllCars().size(), is(2));
    }

    @Test
    public void getAll_CarsAbsent_successful() {
        String jsonCars = "[]";
        stubFor(get("/cars")
                .willReturn(ok(jsonCars)));
        service.getAllCars().toArray();
        assertThat(service.getAllCars().size(), is(0));
    }

    @Test
    public void delete_successful() {
        String jsonCars = "[]";
        stubFor(get("/cars")
                .willReturn(ok()));
        service.getAllCars().toArray();
        assertThat(service.getAllCars().size(), is(0));
    }
}

