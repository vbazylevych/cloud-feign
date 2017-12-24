package com.playtika.qa.carsshop.domain;

import lombok.Builder;

@Builder
public class Car {
    private String plateNumber;
    private String model;
    private String color;
    private int year;
}
