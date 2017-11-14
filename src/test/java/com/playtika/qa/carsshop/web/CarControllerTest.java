package com.playtika.qa.carsshop.web;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CarControllerTest {
    @Mock
    private CarService carService;

    private CarController controller;

    private MockMvc mockMvc;

    @Before
    public void init() {
        controller = new CarController(carService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void name() throws Exception {
        Car car = new Car(1, "", "", 1);
        CarInStore carInStore = new CarInStore(car, 10, "cont");

        when(carService.addCarToStore(carInStore)).thenReturn(carInStore);
        long id = controller.createCar(10, "cont", car);
        assertThat(id, is(1L));
    }
}
