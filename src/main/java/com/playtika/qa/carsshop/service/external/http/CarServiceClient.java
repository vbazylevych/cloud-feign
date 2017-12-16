package com.playtika.qa.carsshop.service.external.http;


import com.playtika.qa.carsshop.service.external.domain.CarRegisterRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


public interface CarServiceClient {

    @RequestLine("POST /cars/?price={price}&contact={contact}")
    @Headers("Content-Type: application/json")
    public long createCar(@Param("price") int price,
                          @Param("contact") String contactDetails,
                           CarRegisterRequest car);
}
