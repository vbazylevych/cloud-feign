package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.dao.entity.AdsEntity;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.dao.entity.UserEntity;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {
    private final Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public CarInStore add(CarInStore carInStore) {

        AdsEntity newAdsEntity = new AdsEntity(persistAndGetUserEntity(carInStore),
                persistAndGetCarEntity(carInStore),
                carInStore.getCarInfo().getPrice(), null);

        em.persist(newAdsEntity);
        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(Integer id) {
        AdsEntity adsEntity = em.find(AdsEntity.class, id);
        CarInStore carInStore = null;
        if (adsEntity != null) {
            carInStore = getCarInStoreFromAds(adsEntity);
        }
        return Optional.ofNullable(carInStore);
    }

    @Override
    public Collection<CarInStore> getAll() {
        TypedQuery<AdsEntity> query = em.createQuery("from AdsEntity", AdsEntity.class);
        List<AdsEntity> adsList = query.getResultList();

        return query.getResultList()
                .stream()
                .map(CarServiceRepositoryImpl::getCarInStoreFromAds)
                .collect(Collectors.toMap(CarInStore::hashCode, Function.identity()))
                .values();
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        CarEntity carEntity = em.find(CarEntity.class, id);

        if (carEntity == null) {
            log.info("Car {} was deleted", id);
            return false;
        } else {

            TypedQuery<AdsEntity> query = em.createQuery("from AdsEntity a where a.car=:id", AdsEntity.class);
            query.setParameter("id", carEntity);
            List<AdsEntity> adsList = query.getResultList();
            adsList.forEach(item->em.remove(item));

            em.remove(carEntity);



            return true;
        }
    }

    private static CarInStore getCarInStoreFromAds(AdsEntity ads) {
        CarEntity carEntity = ads.getCar();
        Car car = new Car(carEntity.getId(), carEntity.getPlate_number(),
                carEntity.getModel(), carEntity.getColor(), carEntity.getYear());
        CarInfo carInfo = new CarInfo(ads.getPrice(), ads.getUser().getContact());

        return new CarInStore(car, carInfo);
    }

    private CarEntity persistAndGetCarEntity(CarInStore carInStore) {
        Car newCar = carInStore.getCar();
        CarEntity newCarEntity = new CarEntity(newCar.getPlate_number(),
                newCar.getModel(), newCar.getYear(), newCar.getColor());

        em.persist(newCarEntity);
        em.flush();
        Integer carId = newCarEntity.getId();
        carInStore.getCar().setId(carId);
        return newCarEntity;
    }

    private UserEntity persistAndGetUserEntity(CarInStore carInStore) {
        UserEntity newUserEntity = new UserEntity("Name", "",
                carInStore.getCarInfo().getContact());

        em.persist(newUserEntity);
        em.flush();
        return newUserEntity;
    }
}
