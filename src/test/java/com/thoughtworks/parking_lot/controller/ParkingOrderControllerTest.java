package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
class ParkingOrderControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private static ObjectMapper objectMapper;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @BeforeAll
    static void initAll() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void initEach() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void should_create_a_parking_order_normally() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(100);
        parkingLot.setLocation("World");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setCarNumber("00962345");

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/parkinglots/{name}/parkingorders", parkingLot.getName())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(parkingOrder))
        );

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parkingLot.name", Matchers.is(parkingLot.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carNumber", Matchers.is(parkingOrder.getCarNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createTime", Matchers.greaterThan(0L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(true)));
    }

    @Test
    void should_update_a_parking_order_when_fetch_by_parking_lot_name_and_car_number_normally() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(10);
        parkingLot.setLocation("World");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setCarNumber("00962345");

        mvc.perform(
                MockMvcRequestBuilders.post("/parkinglots/{name}/parkingorders", parkingLot.getName())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(parkingOrder))
        );
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.put("/parkinglots/{parkingLotName}/parkingorders/{carName}",
                                           parkingLot.getName(), parkingOrder.getCarNumber())
        );

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parkingLot.name", Matchers.is(parkingLot.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carNumber", Matchers.is(parkingOrder.getCarNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createTime", Matchers.greaterThan(0L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime", Matchers.greaterThan(0L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(false)));
    }

    @Test
    void should_create_a_parking_order_fail_when_parking_lot_full_normally() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("aaa");
        parkingLot.setCapacity(0);
        parkingLot.setLocation("World");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setCarNumber("00962345");

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/parkinglots/{name}/parkingorders", parkingLot.getName())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(parkingOrder))
        );

        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("停车场已经满"));
    }
}
