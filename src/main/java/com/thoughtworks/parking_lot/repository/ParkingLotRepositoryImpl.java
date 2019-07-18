package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;

public class ParkingLotRepositoryImpl implements IParkingLotRepository {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot create(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public void deleteByName(String name) {
        parkingLotRepository.deleteById(name);
    }
}
