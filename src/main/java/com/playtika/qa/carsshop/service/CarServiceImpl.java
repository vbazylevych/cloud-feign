package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.CarInStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    public Map<String, Object> getCar(long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("price:", storedCars.get(id).getPrice());
            response.put("contact:", storedCars.get(id).getContact());
            log.info("Car with id {} was successfully founded", id);
        } catch (Exception e) {
            log.info("Can't find car with id {}", id);
        }
        return response;
    }

    @Override
    public Collection<CarInStore> getAllCars() {
        log.info("All cars were returned");
        return storedCars.values();
    }

    @Override
    public void deleteCar(long id) {
        if (storedCars.containsKey(id)) {
            storedCars.remove(id);
            log.info("Car {} was deleted", id);
        } else {
            log.warn("Cant delete car with id {}", id);
        }
    }
}
