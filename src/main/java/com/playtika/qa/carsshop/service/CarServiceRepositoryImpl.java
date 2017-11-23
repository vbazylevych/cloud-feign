package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class CarServiceRepositoryImpl implements CarServiceRepository {
    private final Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public CarInStore add(CarInStore carInStore) {
        carInStore.getCar().setId(id.incrementAndGet());
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
