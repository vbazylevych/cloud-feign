package com.playtika.qa.carsshop.service;


import com.playtika.qa.carsshop.dao.entity.AdsEntity;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.dao.entity.UserEntity;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;

@Slf4j
@AllArgsConstructor
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public CarInStore add(CarInStore carInStore) {

        List<CarEntity> carEntities = findCarEntities(carInStore);
        if (carEntities.isEmpty()) {
            CarEntity newCarEntity = createCarEntity(carInStore);
            UserEntity newUserEntity = createUserEntity(carInStore);
            createAndSaveAdsEntities(carInStore, newUserEntity, newCarEntity);
            setCarId(newCarEntity, carInStore);
            return carInStore;
        }
        CarEntity foundCar = carEntities.get(0);
        if (findOpenAds(foundCar).isEmpty()) {
            UserEntity newUserEntity = createUserEntity(carInStore);
            createAndSaveAdsEntities(carInStore, newUserEntity, foundCar);
            setCarId(foundCar, carInStore);
            return carInStore;
        }
        setCarId(foundCar, carInStore);
        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(long id) {

        return findOpenedAdsByCarId(id)
                .stream()
                .findFirst()
                .map(this::getCarInStoreFromAds);
    }

    @Override
    public Collection<CarInStore> getAll() {
        TypedQuery<AdsEntity> query = em.createQuery("select a from AdsEntity a where a.deal is empty ", AdsEntity.class);
        return query.getResultList()
                .stream()
                .map(this::getCarInStoreFromAds)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    @Override
    public Boolean delete(long id) {
        int deletedCount = em.createQuery("delete from CarEntity where id=:id")
                .setParameter("id", id)
                .executeUpdate();
        if (deletedCount > 0) {
            return true;
        }
        return false;
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
        em.persist(adsEntity);
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

    private List<CarEntity> findCarEntities(CarInStore carInStore) {
        return em.createQuery("from CarEntity where plate_number=:number ", CarEntity.class)
                .setParameter("number", carInStore.getCar().getPlateNumber())
                .getResultList();
    }

    private List<AdsEntity> findOpenAds(CarEntity carEntity) {
        return em.createQuery("from AdsEntity a  where a.car=:car and a.deal is empty", AdsEntity.class)
                .setParameter("car", carEntity)
                .getResultList();
    }

    private List<AdsEntity> findOpenedAdsByCarId(long id) {

        return em.createQuery("select a from AdsEntity a join a.car c  where c.id=:id and a.deal is empty ", AdsEntity.class)
                .setParameter("id", id)
                .getResultList();
    }
}
