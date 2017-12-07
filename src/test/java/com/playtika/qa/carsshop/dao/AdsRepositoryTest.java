package com.playtika.qa.carsshop.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.playtika.qa.carsshop.dao.entity.AdsEntityRepository;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class AdsRepositoryTest extends AbstractDaoTest<AdsEntityRepository> {
    @Test
    @DataSet("filled-car-table.xml")
    public void findByPlatNumberReturnsCarIfPresent() {
      /*  List<CarEntity> result = dao.findByPlateNumber("test");
        CarEntity expectedResult = new CarEntity("test", "opel", 2000, "red");
        expectedResult.setId(1L);
        assertThat(result, hasItem(samePropertyValuesAs(expectedResult))); */
    }

}
