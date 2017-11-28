package com.playtika.qa.carsshop.web;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Matcher;

import static org.hamcrest.CoreMatchers.*;
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
    public void addCar() throws Exception {
        String firstCar = "{\"plate_number\": \"1\", \"color\": \"\", \"model\": \"\", \"year\": 2000 } ";
        addCarInStore(firstCar);
    }

    @Test
    public void getCar() throws Exception {
        String firstCar = "{\"plate_number\": \"1\", \"color\": \"\", \"model\": \"\", \"year\": 2000 } ";
        String id = addCarInStore(firstCar);

        mockMvc.perform(get("/cars/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price").value("10"))
                .andExpect(jsonPath("contact").value("cont"));
    }


    @Test
    public void getAllCars() throws Exception {
        String firstCar = "{\"plate_number\": \"3\", \"color\": \"\", \"model\": \"\", \"year\": 2000 } ";
        String secondCar = "{\"plate_number\": \"4\", \"color\": \"\", \"model\": \"\", \"year\": 2000 } ";
        addCarInStore(firstCar);
        addCarInStore(secondCar);

        mockMvc.perform(get("/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delerteCar() throws Exception {
        String firstCar = "{\"plate_number\": \"5\", \"color\": \"\", \"model\": \"\", \"year\": 2000 } ";
        String id = addCarInStore(firstCar);

        mockMvc.perform(delete("/cars/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String addCarInStore(String car) throws Exception {
        return mockMvc.perform(post("/cars?price=10&contact=cont")
                .content(car)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}




