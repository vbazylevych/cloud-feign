package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.dao.entity.CarEntity;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    private final AtomicLong id = new AtomicLong(0);
    private EntityManager em;

    @Override
    public CarInStore add(CarInStore carInStore) {
        Query query = em.createQuery("select c.id from CarEntity c join c.owner o" +
                "where c.year=2015 and o.name = :name");

        query.setParameter("year", 2015);
        carInStore.getCar().setId(id.incrementAndGet());
        List resultList <CarEntity> = query.getResultList();
        resultList.get(0).
        storedCars.put(id.get(), carInStore);
        log.info("Car with id {} was successfully created", id);
        return carInStore;
    }

    @Override
    public Optional<CarInStore> get(long id) {
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
