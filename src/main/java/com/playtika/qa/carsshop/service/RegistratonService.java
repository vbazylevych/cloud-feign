package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.service.external.CarServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.playtika.qa.carsshop.domain.CarInStope;
import com.playtika.qa.carsshop.domain.Car;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class RegistratonService {

    CarServiceClient carServiceClient;

    public List<Long> processFileAndRegisterCar(Path path) throws Exception {
        List listOfCarId = new ArrayList<>();

        Files.lines(path)
                .map(this::lineToCarInStore)
                .forEach(carInStope -> register(carInStope, listOfCarId));

        return listOfCarId;
    }

    private void register(CarInStope carInStope, List listOfCarId) {
        long carId = carServiceClient.createCar(carInStope.getPrice(), carInStope.getContact(), carInStope.getCar());
        listOfCarId.add(carId);
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

