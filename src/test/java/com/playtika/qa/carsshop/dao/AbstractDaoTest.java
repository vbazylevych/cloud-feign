package com.playtika.qa.carsshop.dao;

import com.github.database.rider.core.DBUnitRule;

import com.playtika.qa.carsshop.dao.entity.AdsEntityRepository;
import com.playtika.qa.carsshop.dao.entity.CarEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@DataJpaTest

public abstract class AbstractDaoTest<D> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> jdbcTemplate.getDataSource().getConnection());

    @Autowired
    protected D dao;
}
