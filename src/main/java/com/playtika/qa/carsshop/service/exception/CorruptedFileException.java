package com.playtika.qa.carsshop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class CorruptedFileException extends RuntimeException{
    public CorruptedFileException(String message) {
        super(message);
    }
}
