package com.playtika.qa.carsshop.domain;

import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.Map;

@Data
public class Car {
    private Map<String, String> param;
    private int price;
    private String contactDetails;

    public Car(int price, String contactDetails, Map param) {
        this.price = price;
        this.contactDetails = contactDetails;
        this.param = param;
    }
}
