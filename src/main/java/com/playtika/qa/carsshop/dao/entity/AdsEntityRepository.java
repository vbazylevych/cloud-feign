package com.playtika.qa.carsshop.dao.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdsEntityRepository extends JpaRepository<AdsEntity,Long> {
    List<AdsEntity> findByDealIsNull();

    List<AdsEntity> findByCarAndDealIsNull(CarEntity carEntity);

    @Query("select a from AdsEntity a join a.car c  where c.id=:id and a.deal is empty ")
    List<AdsEntity> findOpenedAdsByCarId(@Param("id") long id);
}
