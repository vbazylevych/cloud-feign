package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.service.external.CarServiceClient;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @InjectMocks
    RegistratonService registratonService;
    @Mock
    CarServiceClient carServiceClient;

    @Test
    public void registration_multiLinesFile_successful() throws Exception {

        Car firstCar = Car.builder()
                .color("red")
                .model("opel")
                .plateNumber("xxx")
                .year(2017)
                .build();
        Car secondCar = Car.builder()
                .color("red2")
                .model("opel2")
                .plateNumber("xxx2")
                .year(2018)
                .build();

        Path path = Paths.get("test.csv");

        when(carServiceClient.createCar(1000, "kot", firstCar)).thenReturn(1L);
        when(carServiceClient.createCar(1002, "kot2", secondCar)).thenReturn(2L);

        registratonService.processFileAndRegisterCar(path);

        assertThat(registratonService.processFileAndRegisterCar(path), CoreMatchers.is(asList(1L, 2L)));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void registration_emptyFile_successful() throws Exception {
        Path path = Paths.get("empty.csv");
        registratonService.processFileAndRegisterCar(path);
    }
    @Test(expected = NumberFormatException.class)
    public void registration_corruptedFile_successful() throws Exception {
        Path path = Paths.get("corrupted.csv");
        registratonService.processFileAndRegisterCar(path);
    }

}
