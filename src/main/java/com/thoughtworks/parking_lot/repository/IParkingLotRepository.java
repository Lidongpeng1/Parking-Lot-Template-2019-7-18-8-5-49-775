package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;

public interface IParkingLotRepository {
    ParkingLot create(ParkingLot parkingLot);

    void deleteByName(String id);
}
