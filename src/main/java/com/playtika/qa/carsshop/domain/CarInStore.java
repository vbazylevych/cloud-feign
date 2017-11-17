package com.playtika.qa.carsshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CarInStore {
    private final Car car;
    private final CarInfo carInfo;
}
