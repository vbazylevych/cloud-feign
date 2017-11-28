package com.playtika.qa.carsshop.domain;

import lombok.*;

import javax.persistence.Entity;
import java.util.concurrent.atomic.AtomicLong;


@Data
@AllArgsConstructor
public class Car {
    private long id;
    private final String plate_number;
    private String model="";
    private String color="";
    private int year=1901;

    public Car(String plate_number) {
        this.plate_number = plate_number;
    }
}
