package com.playtika.qa.carsshop.service.external;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServceFeignConfiguration {
    @Bean
    public Encoder encoder() {
        return new GsonEncoder();
    }

    @Bean
    public Decoder decoder() {
        return new GsonDecoder();
    }

    @Bean
    ErrorDecoder registrationBackendClientErrorDecoder() {
        return new CarServiceClientErrorDecoder();
    }
}
