package com.playtika.qa.carsshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;


@SpringBootApplication
public class CarsShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarsShopApplication.class, args);
    }

}
