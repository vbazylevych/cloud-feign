package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.service.external.CarServiceClient;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadyOnSaleException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.Car;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.*;
import static java.util.Optional.of;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    CarServiceClient carServiceClient;

    @Override
    public List<Long> processFileAndRegisterCars(String url) throws Exception {
        List<CarInStore> listOfCarsInStore = processFile(url);
        return listOfCarsInStore.stream().map(this::register).filter(id -> id.isPresent())
                .map(id -> id.get()).collect(Collectors.toList());
    }

    private List<CarInStore> processFile(String url) throws IOException {
        return Files.lines(Paths.get(url))
                .map(this::lineToCarInStore).collect(Collectors.toList());
    }

    private Optional<Long> register(CarInStore carInStore) {
        try {
            return of(carServiceClient.createCar(carInStore.getPrice(), carInStore.getContact(), carInStore.getCar()));
        } catch (CarAlreadyOnSaleException e) {
            return empty();
        }
    }

    private CarInStore lineToCarInStore(String line) throws NumberFormatException {
        List<String> words = Stream.of(line.split(","))
                .collect(Collectors.toList());

        Car car = Car.builder()
                .plateNumber(words.get(0))
                .model(words.get(1))
                .color(words.get(2))
                .year(Integer.parseInt(words.get(3)))
                .build();
        CarInStore carInStore = CarInStore.builder()
                .car(car)
                .contact(words.get(4))
                .price(Integer.parseInt(words.get(5)))
                .build();
        return carInStore;
    }
}

