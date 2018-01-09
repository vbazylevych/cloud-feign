package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping(value = "/file")
    public List<Long> processFile(@RequestBody String url) throws Exception {
        return service.processFileAndRegisterCars(url);
    }
}
