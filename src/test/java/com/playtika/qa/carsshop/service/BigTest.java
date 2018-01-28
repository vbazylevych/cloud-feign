package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.BestDealResponse;
import com.playtika.qa.carsshop.domain.User;
import com.playtika.qa.carsshop.service.external.CarServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        log.info("Info from file was loades successful");

        long adsId = listOfAdsIds.get(0);
        long dealId1 = carServiceClient.createDeal(100500, adsId, user);
        long dealId2 = carServiceClient.createDeal(100501, adsId, user);
        log.info("Deals was successfully created!");

        BestDealResponse bestDealResponse = carServiceClient.acceptBestDeal(adsId);
        assertThat(bestDealResponse.getId(), is(dealId2));
        assertThat(bestDealResponse.getPrice(), is(100501
        ));
        log.info("Best deal was accepted successfully");

        carServiceClient.rejectDeal(dealId1);
        log.info("Bad deal was successfully rejected");
    }
}
