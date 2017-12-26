package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.service.external.CarServiceClient;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadyOnSaleException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.playtika.qa.carsshop.domain.CarInStope;
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
public class RegistrationService {

    CarServiceClient carServiceClient;

    public List<Long> processFileAndRegisterCar(String url) throws Exception {
        List<CarInStope> listOfCarsInStore = processFile(url);
        return listOfCarsInStore.stream().map(this::register).filter(id -> id.isPresent())
                .map(id -> id.get()).collect(Collectors.toList());
    }

    private List<CarInStope> processFile(String url) throws IOException {
        return Files.lines(Paths.get(url))
                .map(this::lineToCarInStore).collect(Collectors.toList());
    }

    private Optional<Long> register(CarInStope carInStope) {
        try {
            return of(carServiceClient.createCar(carInStope.getPrice(), carInStope.getContact(), carInStope.getCar()));
        } catch (CarAlreadyOnSaleException e) {
            return empty();
        }
    }

    private CarInStope lineToCarInStore(String line) throws NumberFormatException {
        List<String> words = Stream.of(line.split(","))
                .collect(Collectors.toList());

        Car car = Car.builder()
                .plateNumber(words.get(0))
                .model(words.get(1))
                .color(words.get(2))
                .year(Integer.parseInt(words.get(3)))
                .build();
        CarInStope carInStope = CarInStope.builder()
                .car(car)
                .contact(words.get(4))
                .price(Integer.parseInt(words.get(5)))
                .build();
        return carInStope;
    }
}

