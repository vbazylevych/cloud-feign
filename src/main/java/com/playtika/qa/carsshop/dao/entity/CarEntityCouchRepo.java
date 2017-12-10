package com.playtika.qa.carsshop.dao.entity;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface CarEntityCouchRepo extends CouchbaseRepository<CarEntity, Long> {
    List<CarEntity> findByPlateNumber(String s);
}
