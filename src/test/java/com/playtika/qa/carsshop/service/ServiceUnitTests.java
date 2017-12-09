package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.dao.entity.*;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceUnitTests {
    private CarService carService;
    @Mock
    private AdsEntityRepository adsEntityRepository;
    @Mock
    private CarEntityRepository carEntityRepository;


    @Before
    public void init() {
        carService = new CarServiceImpl(adsEntityRepository, carEntityRepository);
    }

    @Test
    public void allCarsReturnsListOfAdsIfPresent() {
        CarInStore first = new CarInStore(new Car(1L, "1"), new CarInfo(1, "с1"));
        CarInStore second = new CarInStore(new Car(2L, "2"), new CarInfo(2, "с2"));

        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), createCarEntity(first, 1L));
        AdsEntity secondAds = createAdsEntities(second, createUserEntity(second), createCarEntity(second, 2L));

        List<AdsEntity> result = new ArrayList<>();
        result.add(firstAds);
        result.add(secondAds);

        when(adsEntityRepository.findByDealIsNull()).thenReturn(result);
        Collection<CarInStore> allCars = carService.getAll();
        assertThat(allCars.size(), is(2));
        assertThat(allCars, hasItem(first));
        assertThat(allCars, hasItem(second));
    }

    @Test
    public void allCarsReturnsEmptyResponseIfRepositoryIsEmpty() {
        when(adsEntityRepository.findByDealIsNull()).thenReturn(Collections.EMPTY_LIST);
        assertThat(carService.getAll(), is(empty()));
    }

    @Test
    public void getCarReturnsAppropriateCarInfo() {
        CarInStore first = new CarInStore(new Car(1L, "1"), new CarInfo(1, "Sema"));
        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), createCarEntity(first, 1L));
        List<AdsEntity> result = new ArrayList<>();
        result.add(firstAds);

        when(adsEntityRepository.findByCarIdAndDealIsNull(1)).thenReturn(result);

        assertThat(1, is(carService.get(1).get().getCarInfo().getPrice()));
        assertThat("Sema", is(carService.get(1).get().getCarInfo().getContact()));
    }

    @Test
    public void getCarReturnsEmptyResponseIfCarIsAbsent() {
        when(adsEntityRepository.findByCarIdAndDealIsNull(1)).thenReturn(Collections.EMPTY_LIST);
        assertFalse(carService.get(1).isPresent());
    }

    @Test
    public void deleteCarCallsDaoDeleteMethod() {
        carService.delete(1L);
        verify(carEntityRepository).delete(1L);
    }

    private AdsEntity createAdsEntities(CarInStore carInStore, UserEntity user, CarEntity car) {
        AdsEntity adsEntity = new AdsEntity(user, car,
                carInStore.getCarInfo().getPrice(), null);
        return adsEntity;
    }

    private CarEntity createCarEntity(CarInStore carInStore, Long id) {
        Car newCar = carInStore.getCar();
        CarEntity carEntity = new CarEntity(newCar.getPlateNumber(),
                newCar.getModel(), newCar.getYear(), newCar.getColor());
        carEntity.setId(id);
        return carEntity;
    }

    private UserEntity createUserEntity(CarInStore carInStore) {
        UserEntity userEntity = new UserEntity("Name", "", carInStore.getCarInfo().getContact());
        return userEntity;
    }
}

