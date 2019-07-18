package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ParkingLot> getParkingLotByPage(String page, String pageSize) {
        Page<ParkingLot> parkingLots = parkingLotRepository.findAll(PageRequest.of(Integer.valueOf(page) - 1, Integer.valueOf(pageSize)));
        return parkingLots.getContent();

    }

    public ParkingLot findParkingLotByID(String name) {
        return parkingLotRepository.findById(name).get();

    }

}