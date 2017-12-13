package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.dao.entity.*;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTests {

   @InjectMocks
    private CarServiceImpl carServiceImpl;
    @Mock
    private AdsEntityRepository adsEntityRepository;
    @Mock
    private CarEntityRepository carEntityRepository;

    @Test
    public void addNewAdsThenCarIsAbsent() {
        CarInStore first = new CarInStore(new Car(1L, "xxx"), new CarInfo(1, "Sema"));
        CarEntity carEntity = createCarEntity(first, 1L);
        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), carEntity);
        firstAds.setId(1L);

        when(carEntityRepository.findByPlateNumber("xxx")).thenReturn(Collections.EMPTY_LIST);
        when(adsEntityRepository.save(notNull(AdsEntity.class))).thenReturn(firstAds);
        CarInStore resalt = carServiceImpl.add(first);

        assertThat(resalt.getCar().getPlateNumber(), is("xxx"));
        assertThat(resalt.getCarInfo().getPrice(), is(1));
        assertThat(resalt.getCarInfo().getContact(), is("Sema"));
    }

    @Test
    public void addNewAdsThenCarIsPresentAndClosedAdsExist() {
        CarInStore first = new CarInStore(new Car(1L, "xxx"), new CarInfo(1, "Sema"));
        CarEntity carEntity = createCarEntity(first, 1L);
        List<CarEntity> carEntityList = new ArrayList<>();
        carEntityList.add(carEntity);
        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), carEntity);
        firstAds.setId(1L);

        when(carEntityRepository.findByPlateNumber("xxx")).thenReturn(carEntityList);
        when(adsEntityRepository.findByCarIdAndDealIsNull(1L)).thenReturn(Collections.EMPTY_LIST);

        when(adsEntityRepository.save(notNull(AdsEntity.class))).thenReturn(firstAds);
        CarInStore resalt = carServiceImpl.add(first);

        assertThat(resalt.getCar().getPlateNumber(), is("xxx"));
        assertThat(resalt.getCarInfo().getPrice(), is(1));
        assertThat(resalt.getCarInfo().getContact(), is("Sema"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNewAdsThenCarIsPresentAndOpenAdsExistThrowsException() {
        CarInStore first = new CarInStore(new Car(1L, "xxx"), new CarInfo(1, "Sema"));
        CarEntity carEntity = createCarEntity(first, 1L);
        List<CarEntity> carEntityList = new ArrayList<>();
        carEntityList.add(carEntity);
        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), carEntity);
        firstAds.setId(1L);
        List<AdsEntity> adsList = new ArrayList<>();
        adsList.add(firstAds);

        when(carEntityRepository.findByPlateNumber("xxx")).thenReturn(carEntityList);
        when(adsEntityRepository.findByCarIdAndDealIsNull(1L)).thenReturn(adsList);

        when(adsEntityRepository.save(notNull(AdsEntity.class))).thenReturn(firstAds);
        CarInStore resalt = carServiceImpl.add(first);
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
        Collection<CarInStore> allCars = carServiceImpl.getAll();
        assertThat(allCars.size(), is(2));
        assertThat(allCars, hasItem(first));
        assertThat(allCars, hasItem(second));
    }

    @Test
    public void allCarsReturnsEmptyResponseIfRepositoryIsEmpty() {
        when(adsEntityRepository.findByDealIsNull()).thenReturn(Collections.EMPTY_LIST);
        assertThat(carServiceImpl.getAll(), is(empty()));
    }

    @Test
    public void getCarReturnsAppropriateCarInfo() {
        CarInStore first = new CarInStore(new Car(1L, "1"), new CarInfo(1, "Sema"));
        AdsEntity firstAds = createAdsEntities(first, createUserEntity(first), createCarEntity(first, 1L));
        List<AdsEntity> result = new ArrayList<>();
        result.add(firstAds);

        when(adsEntityRepository.findByCarIdAndDealIsNull(1)).thenReturn(result);

        assertThat(1, is(carServiceImpl.get(1).get().getCarInfo().getPrice()));
        assertThat("Sema", is(carServiceImpl.get(1).get().getCarInfo().getContact()));
    }

    @Test
    public void getCarReturnsEmptyResponseIfCarIsAbsent() {
        when(adsEntityRepository.findByCarIdAndDealIsNull(1)).thenReturn(Collections.EMPTY_LIST);
        assertFalse(carServiceImpl.get(1).isPresent());
    }

    @Test
    public void deleteCarCallsDaoDeleteMethod() {
        carServiceImpl.delete(1L);
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

