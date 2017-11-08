package com.playtika.qa.carsshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarInStore {
    private Car car;
    private int price;
    private String contact;
}
