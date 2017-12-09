package com.playtika.qa.carsshop.domain;

import lombok.*;


@Data
@AllArgsConstructor
public class Car {
    private long id;
    private final String plateNumber;
    private String model = "";
    private String color = "";
    private int year = 1901;

    public Car(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Car(long id, String plateNumber) {
        this.id = id;
        this.plateNumber = plateNumber;
    }
}
