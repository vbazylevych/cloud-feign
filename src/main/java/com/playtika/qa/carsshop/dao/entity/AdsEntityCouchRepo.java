package com.playtika.qa.carsshop.dao.entity;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface AdsEntityCouchRepo extends CouchbaseRepository<AdsEntity, Long> {
    List<AdsEntity> findByDealIsNull();

    List<AdsEntity> findByCarIdAndDealIsNull(long id);
}
