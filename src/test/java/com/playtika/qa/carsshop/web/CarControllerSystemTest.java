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
        String firstCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        mockMvc.perform(post("/cars?price=10&contact=cont")
                .content(firstCar).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCar() throws Exception {
        String firstCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        String id = mockMvc.perform(post("/cars?price=10&contact=cont")
                .content(firstCar)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/cars/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price").value("10"))
                .andExpect(jsonPath("contact").value("cont"));
    }

    @Test
    public void getAllCars() throws Exception {
        String firstCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        String secondCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        mockMvc.perform(post("/cars?price=10&contact=cont")
                .content(firstCar)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/cars?price=100&contact=cont2")
                .content(secondCar)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }





    public void delerteCar() throws Exception {
        String firstCar = "{\"enginePower\": 1, \"color\": \"\", \"model\": \"\", \"id\": 1 } ";
        String id = mockMvc.perform(post("/cars?price=10&contact=cont")
                .content(firstCar)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(delete("/cars/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}




