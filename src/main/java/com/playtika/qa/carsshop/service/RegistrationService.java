package com.playtika.qa.carsshop.service;

import java.util.List;

public interface RegistrationService {
    List<Long> processFileAndRegisterCars(String url) throws Exception;
}
