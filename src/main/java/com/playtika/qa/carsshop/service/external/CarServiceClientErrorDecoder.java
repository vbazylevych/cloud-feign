package com.playtika.qa.carsshop.service.external;

import com.playtika.qa.carsshop.service.external.exception.BadRequestException;
import com.playtika.qa.carsshop.service.external.exception.CarAlreadyOnSaleException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarServiceClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Process error response during request: {}", methodKey);
        if (response.status() == 302) {
            return new CarAlreadyOnSaleException("Car already on sale!");
        }

        if (response.status() == 400) {
            return new BadRequestException(response.body().toString());
        }
        return createFeignException(methodKey, response);
    }

    private Exception createFeignException(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        log.warn("Service returns unpredictable response: {}", feignException);
        return feignException;
    }
}
