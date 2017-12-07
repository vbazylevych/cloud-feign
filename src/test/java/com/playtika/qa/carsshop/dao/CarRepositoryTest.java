package com.playtika.qa.carsshop.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.dao.entity.CarEntityRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.samePropertyValuesAs;


public class CarRepositoryTest extends AbstractDaoTest<CarEntityRepository> {


    @Test
    @DataSet("empty-car.xml")
    @ExpectedDataSet("filled-car-table.xml")
    @Commit
    public void booksMayBeStored() {
        dao.save(new CarEntity("test", "opel", 2000, "red"));
    }

    @Test
    @DataSet("filled-car-table.xml")
    public void findByPlatNumberReturnsCarIfPresent() {
        List<CarEntity> result = dao.findByPlateNumber("test");
        CarEntity expectedResult = new CarEntity("test", "opel", 2000, "red");
        expectedResult.setId(1L);
        assertThat(result, hasItem(samePropertyValuesAs(expectedResult)));
    }

    @Test
    @DataSet("filled-car-table.xml")
    public void ifThereIsNoCarWithSuchPlateNumberEmptyListIsReturned() {
        assertThat(dao.findByPlateNumber("xxx"), Matchers.is(empty()));
    }

    @Test
    @DataSet("filled-car-table.xml")
    public void deleteCar() {
        dao.delete(1L);
        assertThat(dao.findAll(), Matchers.is(empty()));
    }


}
