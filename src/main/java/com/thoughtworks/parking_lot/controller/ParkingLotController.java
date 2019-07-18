package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/parkinglots")
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping()
    public ParkingLot create(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.create(parkingLot);
    }

    @GetMapping(params = {"page","pageSize"})
    public ResponseEntity getParkingLotByPage(@RequestParam("page")String page,@RequestParam("pageSize")String pageSize) {
        List<ParkingLot> parkingLots=parkingLotService.getParkingLotByPage(page,pageSize);
        return ResponseEntity.ok().body(parkingLots);
    }

    @DeleteMapping(path = "/{parkingLotName}")
    public ResponseEntity<Object> delete(@PathVariable String parkingLotName) {
        return parkingLotService.delete(parkingLotName)
                .map(parkingLot -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{parkingLotName}")
    public ResponseEntity getParkingLotByID(@PathVariable("parkingLotName") String name) {
        ParkingLot parkingLot=parkingLotService.findParkingLotByID(name);
        return ResponseEntity.ok().body(parkingLot);
    }
}
