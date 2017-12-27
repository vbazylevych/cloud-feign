package com.playtika.qa.carsshop.service;

import java.util.List;

public interface RegistrationService {
    List<Long> processFileAndRegisterCar(String url) throws Exception;
}
