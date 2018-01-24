package com.playtika.qa.carsshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarInfo {
    private final int price;
    private final String contact;
}
