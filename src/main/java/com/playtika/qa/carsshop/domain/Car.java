package com.playtika.qa.carsshop.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Car {
    private int enginePower;
    private String color;
    private String model;
    private long id;
}
