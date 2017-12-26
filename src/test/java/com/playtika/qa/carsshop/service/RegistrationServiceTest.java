package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.service.external.CarServiceClient;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadySallingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.*;

import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.NoSuchFileException;

import static org.junit.Assert.*;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @InjectMocks
    RegistrationService registrationService;
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

        when(carServiceClient.createCar(1000, "kot", firstCar)).thenReturn(1L);
        when(carServiceClient.createCar(1002, "kot2", secondCar)).thenReturn(2L);

        assertThat(registrationService.processFileAndRegisterCar("src/test/resources/test.csv"), is(asList(1L, 2L)));
    }

    @Test
    public void registration_alreadyRegisteredCar_notInListOfId() throws Exception {

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

        when(carServiceClient.createCar(1000, "kot", firstCar)).thenReturn(1L);
        when(carServiceClient.createCar(1002, "kot2", secondCar)).thenThrow(CarAlreadySallingException.class);

        assertThat(registrationService.processFileAndRegisterCar("src/test/resources/test.csv"), is(asList(1L)));
        assertThat(registrationService.processFileAndRegisterCar("src/test/resources/test.csv").size(), is(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void registration_emptyFile_throwsCorruptedFileException() throws Exception {
        registrationService.processFileAndRegisterCar("src/test/resources/empty.csv");
    }

    @Test(expected = NumberFormatException.class)
    public void registration_corruptedFile_throwsCorruptedFileException() throws Exception {
        registrationService.processFileAndRegisterCar("src/test/resources/corrupted.csv");
    }

    @Test(expected = NoSuchFileException.class)
    public void registration_notFoundFile_throwsNotFoundException() throws Exception {
        registrationService.processFileAndRegisterCar("bla.csv");
    }

}
