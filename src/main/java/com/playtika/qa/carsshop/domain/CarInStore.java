package com.playtika.qa.carsshop.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarInStore {
    private Car car;
    private int price;
    private String contact;
}
