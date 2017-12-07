package com.playtika.qa.carsshop.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.dao.entity.CarEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CarRepositoryTest extends AbstractDaoTest<CarEntityRepository>{



    @Test
    @DataSet("test.xml")
    public void name() throws Exception {
        List<CarEntity> result = dao.findByPlateNumber("100500");
        dao.findById(1);

        assertThat(result.size(), is(1));
    }


}
