package com.playtika.qa.carsshop.service.web;

import com.playtika.qa.carsshop.service.RegistratonService;
import com.playtika.qa.carsshop.web.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static com.google.common.primitives.Longs.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RegistrationController.class)
@RunWith(SpringRunner.class)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistratonService service;

    @Test
    public void processFile_WithExistingFile_successful() throws Exception {

        Path path = Paths.get("test.csv");
        when(service.processFileAndRegisterCar(path)).thenReturn(asList(1L));

        mockMvc.perform(post("/?url=test.csv")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0]").value(1));

    }
}
