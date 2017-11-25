package com.playtika.qa.carsshop.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CarInStore {
    private final Car car;
    private final CarInfo carInfo;
}
