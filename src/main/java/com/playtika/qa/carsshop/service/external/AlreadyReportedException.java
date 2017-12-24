package com.playtika.qa.carsshop.service.external;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.ALREADY_REPORTED)
public class AlreadyReportedException extends RuntimeException{
    public AlreadyReportedException(String message) {
        super(message);
    }
}
