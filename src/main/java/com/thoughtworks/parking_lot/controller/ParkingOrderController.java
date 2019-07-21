package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingOrderController {
    @Autowired
    private ParkingOrderService parkingOrderService;

    @PostMapping(path = "/parkinglots/{parkingLotName}/parkingorders")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingOrder parkCar(@PathVariable String parkingLotName, @RequestBody ParkingOrder parkingOrder) {
        return parkingOrderService.createOrder(parkingLotName, parkingOrder);
    }
}