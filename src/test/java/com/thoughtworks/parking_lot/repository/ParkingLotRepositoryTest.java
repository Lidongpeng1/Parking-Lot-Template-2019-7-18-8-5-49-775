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


}
