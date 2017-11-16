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
public class CarServiceImpl implements CarService {
    private final Map<Long, CarInStore> storedCars = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public CarInStore addCarToStore(CarInStore carInStore) {
        carInStore.getCar().setId(id.incrementAndGet());
        storedCars.put(id.get(), carInStore);
        log.info("Car with id {} was successfully created", id);
        return carInStore;
    }

    @Override
    public Optional<CarInfo> getCar(long id) {
        try {
            return Optional.of(storedCars.get(id).getCarInfo());
        } catch (Exception e) {
            log.error("Cant find car");
            return Optional.empty();
        }
    }

    @Override
    public Collection<CarInStore> getAllCars() {
        log.info("All cars were returned");
        return storedCars.values();
    }

    @Override
    public Boolean deleteCar(long id) {
        CarInStore removed = storedCars.remove(id);
        if (removed.equals(null)) {
            log.info("Car {} was deleted", id);
            return false;
        } else {
            log.warn("Cant delete car with id {}", id);
            return true;
        }
    }
}
