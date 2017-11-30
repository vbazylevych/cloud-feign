package com.playtika.qa.carsshop.domain;

import lombok.*;

@Data
@AllArgsConstructor
public class CarInStore {
    private final Car car;
    private final CarInfo carInfo;
}
