package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.service.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationServiceImpl service;

    @PostMapping
    public List<Long> processFile(@RequestParam("url") String url) throws Exception {
        return service.processFileAndRegisterCar(url);
    }
}
