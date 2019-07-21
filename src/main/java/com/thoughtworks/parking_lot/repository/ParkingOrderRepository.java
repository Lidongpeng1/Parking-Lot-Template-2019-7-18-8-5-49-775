package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Integer> {
    long countByParkingLotName(String parkingLotName);
    List<ParkingOrder> findByParkingLotNameAndCarNumberAndStatus(String parkingLotName, String carName, boolean status);
}
