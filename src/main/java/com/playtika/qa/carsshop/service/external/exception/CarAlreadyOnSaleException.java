package com.playtika.qa.carsshop.service.external.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class CarAlreadyOnSaleException extends RuntimeException {
    public CarAlreadyOnSaleException(String message) {
        super(message);
    }
}
