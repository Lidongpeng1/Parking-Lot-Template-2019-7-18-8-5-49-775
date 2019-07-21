package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingOrderService {
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingOrder createOrder(String parkingLotName, ParkingOrder parkingOrder){
        return parkingLotRepository.findById(parkingLotName)
                .filter(parkingLot -> parkingOrderRepository.countByParkingLotName(parkingLotName) <
                        parkingLot.getCapacity())
                .map(parkingLot -> {
                    parkingOrder.setParkingLot(parkingLot);
                    parkingOrder.setCreateTime(System.currentTimeMillis());
                    parkingOrder.setStatus(true);
                    return parkingOrderRepository.save(parkingOrder);
                })
                .orElse(null);
    }
}
