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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public CarInStore add(CarInStore carInStore) {

        TypedQuery<CarEntity> query = em.createQuery("from CarEntity where plate_number=:number ", CarEntity.class);
        query.setParameter("number", carInStore.getCar().getPlate_number());
        List<CarEntity> carList = query.getResultList();

        if (carList.isEmpty()) {
            CarEntity carEntity = persistAndGetCarEntity(carInStore);
            persistAdsEntities(carInStore, persistAndGetUserEntity(carInStore), carEntity);
            carInStore.getCar().setId(carEntity.getId());
        } else {
            TypedQuery<AdsEntity> adsQuery = em.createQuery("from AdsEntity where car=:car and deal_id is null", AdsEntity.class);
            adsQuery.setParameter("car", carList.get(0));
            List<AdsEntity> adsEList = adsQuery.getResultList();
            if (adsEList.isEmpty()) {
                persistAdsEntities(carInStore, persistAndGetUserEntity(carInStore), carList.get(0));
            }
            carInStore.getCar().setId(carList.get(0).getId());
        }

        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(long id) {
        CarInStore carInStore = null;
        CarEntity car = em.find(CarEntity.class, id);
        TypedQuery<AdsEntity> query = em.createQuery("from AdsEntity where car=:car and deal_id is null", AdsEntity.class);
        query.setParameter("car", car);
        List<AdsEntity> adsEList = query.getResultList();

        if (adsEList.size() > 0) {
            AdsEntity adsEntity = adsEList.get(0);
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
    public Boolean delete(long id) {
        int deletedCount = em.createQuery("delete from CarEntity where id=:id")
                .setParameter("id", id)
                .executeUpdate();

        if (deletedCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    private static CarInStore getCarInStoreFromAds(AdsEntity ads) {
        CarEntity carEntity = ads.getCar();
        Car car = new Car(carEntity.getId(), carEntity.getPlate_number(),
                carEntity.getModel(), carEntity.getColor(), carEntity.getYear());
        CarInfo carInfo = new CarInfo(ads.getPrice(), ads.getUser().getContact());

        return new CarInStore(car, carInfo);
    }

    private void persistAdsEntities(CarInStore carInStore, UserEntity user, CarEntity car) {
        AdsEntity newAdsEntity = new AdsEntity(user, car,
                carInStore.getCarInfo().getPrice(), null);
        em.persist(newAdsEntity);
        em.flush();
    }

    private CarEntity persistAndGetCarEntity(CarInStore carInStore) {
        Car newCar = carInStore.getCar();
        CarEntity newCarEntity = new CarEntity(newCar.getPlate_number(),
                newCar.getModel(), newCar.getYear(), newCar.getColor());
        em.persist(newCarEntity);
        em.flush();
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
