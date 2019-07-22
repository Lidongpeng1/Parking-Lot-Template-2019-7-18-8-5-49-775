package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ParkingLotRepositoryTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    void should_create_a_parking_lot_normally() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("World");
        ParkingLot createdParkingLot = parkingLotRepository.create(parkingLot);

        Assertions.assertNotNull(createdParkingLot);
        Assertions.assertEquals(parkingLot.getName(), createdParkingLot.getName());
        Assertions.assertEquals(parkingLot.getCapacity(), createdParkingLot.getCapacity());
        Assertions.assertEquals(parkingLot.getLocation(), createdParkingLot.getLocation());
    }

    @Test
    void should_delete_a_parking_lot_by_name_normally() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("World");
        ParkingLot createdParkingLot = parkingLotRepository.save(parkingLot);

        parkingLotRepository.deleteByName(createdParkingLot.getName());
        Optional<ParkingLot> result = parkingLotRepository.findById(createdParkingLot.getName());

        Assertions.assertNotNull(createdParkingLot);
        Assertions.assertEquals(parkingLot.getName(), createdParkingLot.getName());
        Assertions.assertEquals(parkingLot.getCapacity(), createdParkingLot.getCapacity());
        Assertions.assertEquals(parkingLot.getLocation(), createdParkingLot.getLocation());
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void should_return_all_parking_lot_pageable_by_page_and_page_size_normally() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("World");
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setName("Hello2");
        parkingLot2.setCapacity(1);
        parkingLot2.setLocation("World");
        ParkingLot createdParkingLot = parkingLotRepository.save(parkingLot);
        ParkingLot createdParkingLot2 = parkingLotRepository.save(parkingLot2);

        List<ParkingLot> parkingLots = parkingLotRepository.findAllPageable(1, 1);

        Assertions.assertNotNull(createdParkingLot);
        Assertions.assertNotNull(createdParkingLot2);
        Assertions.assertEquals(1, parkingLots.size());
    }
}
