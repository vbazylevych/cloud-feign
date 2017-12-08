package com.playtika.qa.carsshop.dao;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.playtika.qa.carsshop.dao.entity.AdsEntity;
import com.playtika.qa.carsshop.dao.entity.AdsEntityRepository;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class AdsRepositoryTest extends AbstractDaoTest<AdsEntityRepository> {
    @Test
    @DataSet(value="filled-ads-table.xml", disableConstraints = true, useSequenceFiltering = false)
    @DBUnit(allowEmptyFields = true)
    public void findByCarIdReturnsOpenAdsIfPresent() {
        List<AdsEntity> result = dao.findByCarIdAndDealIsNull(1);
        assertThat(result.get(0).getId(), is(2L));
    }

    @Test
    @DataSet(value="empty-ads-table.xml", disableConstraints = true, useSequenceFiltering = false)
    @DBUnit(allowEmptyFields = true)
    public void findByCarIdReturnsEmptyListIfTableIsEmpty() {
        assertThat(dao.findByCarIdAndDealIsNull(1), Matchers.is(empty()));
    }
}
