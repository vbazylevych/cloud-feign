package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.dao.entity.AdsEntity;
import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.dao.entity.DealEntity;
import com.playtika.qa.carsshop.dao.entity.UserEntity;
import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {
    private final Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
    //  private final AtomicLong id = new AtomicLong(0);
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public CarInStore add(CarInStore carInStore) {
        //  Query query = em.createQuery("select c.id from CarEntity c join c.owner o" +
        //         "where c.year=2015 and o.name = :name");

        //    query.setParameter("year", 2015);
        //    carInStore.getCar().setId(id.incrementAndGet());
        //  List resultList <CarEntity> = query.getResultList();
        //  resultList.get(0).

        //    long newId = id.incrementAndGet();
        //  carInStore.getCar().setId(newId);
        //   storedCars.put(newId, carInStore);
        Car newCar = carInStore.getCar();
        CarEntity newCarEntity = new CarEntity(newCar.getPlate_number(),
                newCar.getModel(),newCar.getYear(), newCar.getColor());

        em.persist(newCarEntity);
        em.flush();
        Integer carId = newCarEntity.getId();
        carInStore.getCar().setId(carId);

        UserEntity newUserEntity = new UserEntity("Name", "",
                carInStore.getCarInfo().getContact());


        em.persist(newUserEntity);
        em.flush();
        Integer userId = newUserEntity.getId();

        AdsEntity newAdsEntity = new AdsEntity( newUserEntity, newCarEntity,
                carInStore.getCarInfo().getPrice(), null);

        em.persist(newAdsEntity);
        em.flush();
        Integer ads_id = newUserEntity.getId();
        //  log.info("Car with id {} was successfully created", id);
        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(Integer id) {

     /*   Query query = em.createQuery("select a.user, a.price from AdsEntity a" +
                " where a.id=:id");
        query.setParameter("id", id);
        //query.setParameter("deal", null);


        List resultList = query.getResultList();
        Object o = resultList.get(0); */
        AdsEntity adsEntity = em.find(AdsEntity.class, id);

        CarEntity car = adsEntity.getCar();

        CarInStore carInStore = new CarInStore(car, new CarInfo(adsEntity.getPrice(),
                adsEntity.getUser().getContact()));



        return Optional.ofNullable(storedCars.get(id));
    }

    @Override
    public Collection<CarInStore> getAll() {
        log.info("All cars were returned");
        return storedCars.values();
    }

    @Override
    public Boolean delete(long id) {
        if (storedCars.remove(id) == null) {
            log.info("Car {} was deleted", id);
            return false;
        } else {
            log.warn("Cant delete car with id {}", id);
            return true;
        }
    }
}
