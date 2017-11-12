package com.playtika.qa.carsshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInStore {
    private Car car;
    private int price;
    private String contact;
}
