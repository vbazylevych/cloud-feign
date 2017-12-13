package com.playtika.qa.carsshop.dao.entity;


import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AdsEntityRepository extends JpaRepository<AdsEntity, Long>{
    List<AdsEntity> findByDealIsNull();

    List<AdsEntity> findByCarIdAndDealIsNull(long id);
}
