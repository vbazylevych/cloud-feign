package com.playtika.qa.carsshop.web;


import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import com.playtika.qa.carsshop.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void getExistingCar() throws Exception {
        CarInfo response = new CarInfo(1, "cont");
        when(carService.getCar(1)).thenReturn(response);

        mockMvc.perform(get("/cars/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("price").value("1"))
                .andExpect(jsonPath("contact").value("cont"));
    }

    @Test
    public void getNotExistingCar() throws Exception {
        CarInfo response = new CarInfo();
        when(carService.getCar(1)).thenReturn(response);

        mockMvc.perform(get("/cars/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{}"));
    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car(1, "", "", 1);
        CarInStore carInStore = new CarInStore(car, new CarInfo(10, "cont"));

        when(carService.addCarToStore(carInStore)).thenReturn(carInStore);
        String jsonString = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        mockMvc.perform(post("/cars?price=10&contact=cont").content(jsonString).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("1"));
    }

    @Test
    public void ifContactParamIsMissedThrowException() throws Exception {
        mockMvc.perform(post("/cars?price=10").content("{}").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void ifPriceParamIsMissedThrowException() throws Exception {
        mockMvc.perform(post("/cars?contact=jj").content("{}").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getAllCarsReturnsOneCar() throws Exception {
        Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
        storedCars.put(1L, new CarInStore(new Car(10, "red", "opel", 1),
                new CarInfo(1, "con")));
        when(carService.getAllCars()).thenReturn(storedCars.values());

        mockMvc.perform(get("/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].car.enginePower").value(10))
                .andExpect(jsonPath("$[0].car.color").value("red"))
                .andExpect(jsonPath("$[0].car.model").value("opel"))
                .andExpect(jsonPath("$[0].car.id").value(1))
                .andExpect(jsonPath("$[0].carInfo.price").value(1))
                .andExpect(jsonPath("$[0].carInfo.contact").value("con"));

    }

    @Test
    public void getSeveralCars() throws Exception {
        Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
        storedCars.put(1L, new CarInStore(new Car(10, "red", "opel", 1), new CarInfo(1, "con1")));
        storedCars.put(2L, new CarInStore(new Car(20, "blue", "mazda", 2), new CarInfo(2, "con2")));
        storedCars.put(3L, new CarInStore(new Car(30, "black", "reno", 3), new CarInfo(3, "con3")));
        when(carService.getAllCars()).thenReturn(storedCars.values());

        mockMvc.perform(get("/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].car.enginePower").value(10))
                .andExpect(jsonPath("$[0].carInfo.price").value(1))
                .andExpect(jsonPath("$[1].carInfo.contact").value("con2"))
                .andExpect(jsonPath("$[1].car.color").value("blue"))
                .andExpect(jsonPath("$[2].car.model").value("reno"));
    }

    @Test
    public void getNotExistingCars() throws Exception {
        Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
        when(carService.getAllCars()).thenReturn(storedCars.values());

        mockMvc.perform(get("/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("[]"));
    }

    @Test
    public void deleteCar() throws Exception {
        mockMvc.perform(delete("/cars/3").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
