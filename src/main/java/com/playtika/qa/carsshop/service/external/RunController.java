package com.playtika.qa.carsshop.service.external;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@AllArgsConstructor
public class RunController {

    private final Birja service;

    @GetMapping("/run")
    public void registerCar() {
        log.info("Start register car");
        service.register();
    }
}
