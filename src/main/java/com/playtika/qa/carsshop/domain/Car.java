package com.playtika.qa.carsshop.domain;

import lombok.*;

import javax.persistence.Entity;
import java.util.concurrent.atomic.AtomicLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private long id;
    private String plate_number;
    private String model;
    private String color;
    private int year=1990;



}
