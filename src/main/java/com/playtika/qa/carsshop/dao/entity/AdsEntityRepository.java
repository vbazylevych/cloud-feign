package com.playtika.qa.carsshop.dao.entity;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdsEntityRepository extends JpaRepository<AdsEntity, Long>{
    List<AdsEntity> findByDealIsNull();

    List<AdsEntity> findByCarIdAndDealIsNull(long id);
}
