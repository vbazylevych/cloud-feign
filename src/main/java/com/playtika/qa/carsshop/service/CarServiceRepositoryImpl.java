package com.playtika.qa.carsshop.service;


import com.playtika.qa.carsshop.dao.entity.*;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {

    private AdsEntityRepository adsEntityRepository;
    private CarEntityRepository carEntityRepository;

    @Transactional
    @Override
    public CarInStore add(CarInStore carInStore) {

        List<CarEntity> carEntities = findCarByPlateNumber(carInStore.getCar().getPlateNumber());
        if (carEntities.isEmpty()) {
            CarEntity newCarEntity = createCarEntity(carInStore);
            UserEntity newUserEntity = createUserEntity(carInStore);
            createAndSaveAdsEntities(carInStore, newUserEntity, newCarEntity);
            setCarId(newCarEntity, carInStore);
            return carInStore;
        }
        CarEntity foundCar = carEntities.get(0);
        if (findOpenAdsByCarId(foundCar.getId()).size() > 0) {
            throw new IllegalArgumentException("Car already selling!");
        }
        UserEntity newUserEntity = createUserEntity(carInStore);
        createAndSaveAdsEntities(carInStore, newUserEntity, foundCar);
        setCarId(foundCar, carInStore);
        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(long id) {
        return findOpenAdsByCarId(id)
                .stream()
                .findFirst()
                .map(this::getCarInStoreFromAds);
    }

    @Override
    public Collection<CarInStore> getAll() {
        return adsEntityRepository.findByDealIsNull()
                .stream()
                .map(this::getCarInStoreFromAds)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    @Override
    public void delete(long id) {
        carEntityRepository.deleteById(id);
    }

    private CarInStore getCarInStoreFromAds(AdsEntity ads) {
        CarEntity carEntity = ads.getCar();
        Car car = new Car(carEntity.getId(), carEntity.getPlateNumber(),
                carEntity.getModel(), carEntity.getColor(), carEntity.getYear());
        CarInfo carInfo = new CarInfo(ads.getPrice(), ads.getUser().getContact());
        return new CarInStore(car, carInfo);
    }

    private AdsEntity createAndSaveAdsEntities(CarInStore carInStore, UserEntity user, CarEntity car) {
        AdsEntity adsEntity = new AdsEntity(user, car,
                carInStore.getCarInfo().getPrice(), null);
        adsEntityRepository.save(adsEntity);
        return adsEntity;
    }

    private CarEntity createCarEntity(CarInStore carInStore) {
        Car newCar = carInStore.getCar();
        CarEntity carEntity = new CarEntity(newCar.getPlateNumber(),
                newCar.getModel(), newCar.getYear(), newCar.getColor());
        return carEntity;
    }

    private UserEntity createUserEntity(CarInStore carInStore) {
        UserEntity userEntity = new UserEntity("Name", "", carInStore.getCarInfo().getContact());
        return userEntity;
    }

    private void setCarId(CarEntity newCarEntity, CarInStore carInStore) {
        carInStore.getCar().setId(newCarEntity.getId());
    }

    private List<CarEntity> findCarByPlateNumber(String plateNumber) {
        return carEntityRepository.findByPlateNumber(plateNumber);
    }

    private List<AdsEntity> findOpenAdsByCarId(Long id) {
        List<CarEntity> carList = carEntityRepository.findById(id);
        if (carList.isEmpty()){
            return Collections.emptyList();
        }
        return adsEntityRepository.findByCarAndDealIsNull(carList.get(0));
    }
}
