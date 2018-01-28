package com.playtika.qa.carsshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class CarInStore {
    private final Car car;
    private final CarInfo carInfo;
}
