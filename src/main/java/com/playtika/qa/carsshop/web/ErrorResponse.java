package com.playtika.qa.carsshop.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    public final String message;
    public final int code;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
