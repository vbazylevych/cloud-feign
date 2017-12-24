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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (year != car.year) return false;
        if (plateNumber != null ? !plateNumber.equals(car.plateNumber) : car.plateNumber != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        return color != null ? color.equals(car.color) : car.color == null;
    }

}
