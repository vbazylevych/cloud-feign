package com.playtika.qa.carsshop.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CarControllerSystemTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void workWithCarShop() throws Exception {
        String firstCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        String secondCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";

        mockMvc.perform(post("/cars?price=10&contact=cont").content(firstCar).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("1"));
        log.info("First was created");
        mockMvc.perform(post("/cars?price=100&contact=cont2").content(secondCar).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("Second car was created");
        mockMvc.perform(get("/cars/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price").value("10"))
                .andExpect(jsonPath("contact").value("cont"));
        log.info("First car was returned");
        mockMvc.perform(delete("/cars/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("First was deleted");
        mockMvc.perform(get("/cars").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].car.id").value(2));
        log.info("Second car was returned by getAllCars");
    }

}
