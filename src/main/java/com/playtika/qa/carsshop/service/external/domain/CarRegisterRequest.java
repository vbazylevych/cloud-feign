package com.playtika.qa.carsshop.service.external.domain;

import lombok.Builder;
import lombok.Value;

@Builder
public class CarRegisterRequest {
    private final String plateNumber;
    private String model = "";
    private String color = "";
    private int year = 1901;
}
