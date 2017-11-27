package com.playtika.qa.carsshop.service;

import com.playtika.qa.carsshop.domain.Car;
import com.playtika.qa.carsshop.domain.CarInStore;
import com.playtika.qa.carsshop.domain.CarInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ServiceUnitTests {
    private CarServiceRepository carServiceRepository;
    @Autowired
    private EntityManager em;
    @Before
    public void init() {
        carServiceRepository = new CarServiceRepositoryImpl(em);
    }
    @Test
    public void addCarsAssignsSequentialId() {
        CarInStore first = new CarInStore(new Car(-1, "1", "", "",2000), new CarInfo(1, ""));
        CarInStore second = new CarInStore(new Car(-1,"2", "","",2000), new CarInfo(1,""));
        CarInStore firstCarInStore = carServiceRepository.add(first);
        assertEquals(1, firstCarInStore.getCar().getId());
        CarInStore secondCarInStore = carServiceRepository.add(second);
        assertEquals(2, secondCarInStore.getCar().getId());
    }

    @Test
    public void allCarsReturnsCars() {
        CarInStore first = new CarInStore(new Car(1, "1", "", "",2000), new CarInfo(1, "с1"));
        CarInStore second = new CarInStore(new Car(2,"2", "","",2000), new CarInfo(1,"с2"));
        carServiceRepository.add(first);
        carServiceRepository.add(second);
        Collection<CarInStore> allCars = carServiceRepository.getAll();

        assertTrue(allCars.contains(first));
        assertTrue(allCars.contains(second));
        assertThat(allCars.size(), is(2));
    }

    @Test
    public void allCarsReturnsEmptyResponseIfRepositoryIsEmpty() {
        assertThat(carServiceRepository.getAll(), is(empty()));
    }

    @Test
    public void getCarReturnsAppropriateCarInfo() {
        CarInfo expectedResponse = new CarInfo(2, "Sema");
        CarInStore carInStore = new CarInStore(new Car(21,"","","",2000), expectedResponse);
        CarInStore carInStoreWrong = new CarInStore(new Car(), new CarInfo(10, "kot"));
        carServiceRepository.add(carInStore);

        assertEquals(2, carServiceRepository.get(1).get().getCarInfo().getPrice());
        assertEquals("Sema", carServiceRepository.get(1).get().getCarInfo().getContact());
    }

    @Test
    public void getCarReturnsEmptyResponseIfCarIsAbsent() {
        assertFalse(carServiceRepository.get(-10).isPresent());
    }

    @Test
    public void deleteWhenRepositoryIsEmpty() {
        assertFalse(carServiceRepository.delete(3));
        assertTrue(carServiceRepository.getAll().isEmpty());
    }

    @Test
    public void deleteWhenRepositoryHasSeveralItems() {
        CarInStore first = new CarInStore(new Car(), new CarInfo(1, "kot"));
        CarInStore second = new CarInStore(new Car(), new CarInfo(2, "krot"));
        carServiceRepository.add(first);
        carServiceRepository.add(second);
        assertTrue(carServiceRepository.delete(2));
        assertTrue(carServiceRepository.getAll().contains(first));
        assertFalse(carServiceRepository.getAll().contains(second));
        assertThat(carServiceRepository.getAll().size(), is(1));
    }
}

