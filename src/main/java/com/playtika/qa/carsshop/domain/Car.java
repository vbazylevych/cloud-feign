package com.playtika.qa.carsshop.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Car {
    private String plateNumber;
    private String model;
    private String color;
    private int year;
}
