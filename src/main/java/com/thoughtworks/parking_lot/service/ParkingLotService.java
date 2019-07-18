package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot create(ParkingLot parkingLot) {
        return parkingLotRepository.create(parkingLot);
    }

    public Optional<ParkingLot> delete(String name) {
        return parkingLotRepository.findById(name)
                .map(parkingLot -> {
                    parkingLotRepository.deleteByName(name);
                    return parkingLot;
                });
    }
}