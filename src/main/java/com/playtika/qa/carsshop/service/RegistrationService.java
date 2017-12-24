package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.service.external.CarServiceClientImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.playtika.qa.carsshop.domain.CarInStope;
import com.playtika.qa.carsshop.domain.Car;


import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

    CarServiceClientImpl carServiceClient;

    public List<Long> processFileAndRegisterCar(String url) throws Exception {
        List listOfCarId = new ArrayList<>();
        try {
            Files.lines(Paths.get(url))
                    .map(this::lineToCarInStore)
                    .forEach(carInStope -> register(carInStope, listOfCarId));
        } catch (NoSuchFileException e) {
            throw new NotFoundException("File " + e.getMessage() + " not found");
        } catch (java.lang.NumberFormatException e) {
            throw new CorruptedFileException(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            throw new CorruptedFileException("not all mandatory fields present in the file");
        }

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

