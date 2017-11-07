package com.playtika.qa.carsshop.domain;

import lombok.Data;

@Data
public class CarInStore {
    private Car car;
    private int price;
    private String contact;

    public CarInStore(Car car, int price, String contact) {
        this.car = car;
        this.price = price;
        this.contact = contact;
    }
}
