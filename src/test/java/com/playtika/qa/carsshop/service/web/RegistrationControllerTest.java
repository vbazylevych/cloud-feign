package com.playtika.qa.carsshop.service.web;

import com.playtika.qa.carsshop.service.RegistrationService;
import com.playtika.qa.carsshop.service.external.exception.BadRequestException;
import com.playtika.qa.carsshop.web.RegistrationController;
import feign.FeignException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.NoSuchFileException;

import static com.google.common.primitives.Longs.asList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
    private RegistrationService service;

    @Test
    public void processFile_WithExistingFile_successful() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenReturn(asList(1L));
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0]").value(1));
    }

    @Test
    public void processFile_emptyFile_returnNotAcceptableStatus() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenThrow(NumberFormatException.class);
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("code").value(406));
    }

    @Test
    public void processFile_corruptedFile_returnNotAcceptableStatus() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenThrow(IndexOutOfBoundsException.class);
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("code").value(406));
    }

    @Test
    public void processFile_noFile_returnNotFoundStatus() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenThrow(NoSuchFileException.class);
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(404));
    }

    @Test
    public void processFile_handle_BadRequestExceptionFromFeignClient() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenThrow(BadRequestException.class);
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400));

    }

    @Test
    public void processFile_handle_InternalErrort() throws Exception {
        when(service.processFileAndRegisterCar("test.csv")).thenThrow(FeignException.class);
        mockMvc.perform(post("/?url=test.csv")
                .accept(APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("code").value(500));
    }
}
