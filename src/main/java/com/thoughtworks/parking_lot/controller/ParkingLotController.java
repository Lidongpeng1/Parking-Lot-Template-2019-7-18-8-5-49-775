package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/parkinglots")
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping()
    public ParkingLot create(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.create(parkingLot);
    }

    @DeleteMapping(path = "/{parkingLotName}")
    public ResponseEntity<Object> delete(@PathVariable String parkingLotName) {
        return parkingLotService.delete(parkingLotName)
                .map(parkingLot -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
