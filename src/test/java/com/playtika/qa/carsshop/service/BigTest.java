package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.BestDealResponse;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import com.playtika.qa.carsshop.domain.User;
import com.playtika.qa.carsshop.service.external.CarServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
public class BigTest {

    @Autowired
    CarServiceClient carServiceClient;
    @Autowired
    RegistrationService registrationService;

    @Test
    public void car_selling_cycle_test() throws Exception {
        User user = new User("kot", "krot", "064545678");
        List<Long> listOfAdsIds = registrationService.processFileAndRegisterCars("src/test/resources/test.csv");
        assertThat(listOfAdsIds.size(), is(2));
        log.info("Info from file was loaded successful");

        CarInfo carInfoForFirstCar = carServiceClient.getCar(listOfAdsIds.get(0));
        CarInfo carInfoForSecondCar = carServiceClient.getCar(listOfAdsIds.get(1));
        assertThat(carInfoForFirstCar, samePropertyValuesAs(new CarInfo(1000, "kot")));
        assertThat(carInfoForSecondCar, samePropertyValuesAs(new CarInfo(1002, "kot2")));
        log.info("Car info returns correct info about loaded cars");

        Collection<CarInStore> allCars = carServiceClient.getAllCars();
        Optional<CarInStore> firstAds = allCars.stream()
                .filter(a -> a.getCar().getPlateNumber().equals("xxx"))
                .findFirst();
        Optional<CarInStore> secondAds = allCars.stream()
                .filter(a -> a.getCar().getPlateNumber().equals("xxx2"))
                .findFirst();
        assertTrue(firstAds.isPresent());
        assertTrue(secondAds.isPresent());
        log.info("Loaded car present in AllCars response");

        carServiceClient.deleteCars(listOfAdsIds.get(1));
        assertThat(carServiceClient.getAllCars().size(), is(1));
        log.info("Loaded car can be deleted");

        long adsId = listOfAdsIds.get(0);
        long dealId1 = carServiceClient.createDeal(100500, adsId, user);
        long dealId2 = carServiceClient.createDeal(100501, adsId, user);
        log.info("Deals was successfully created!");

        BestDealResponse bestDealResponse = carServiceClient.acceptBestDeal(adsId);
        assertThat(bestDealResponse.getId(), is(dealId2));
        assertThat(bestDealResponse.getPrice(), is(100501
        ));
        assertThat(carServiceClient.getAllCars().size(),is(0));
        log.info("Best deal was accepted successfully");

        carServiceClient.rejectDeal(dealId1);
        log.info("Bad deal was successfully rejected");
    }
}
