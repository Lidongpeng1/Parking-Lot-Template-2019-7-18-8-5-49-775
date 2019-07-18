package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.ParkingLot;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
class ParkingLotControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void initAll() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void initEach() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void should_create_a_parking_lot_normally() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("World");

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/parkinglots")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(parkingLot))
        );

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(parkingLot.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(parkingLot.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is(parkingLot.getLocation())));
    }

    @Test
    void should_delete_a_parking_lot_by_name_normally() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Hello");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("World");

        mvc.perform(
                MockMvcRequestBuilders.post("/parkinglots")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(parkingLot))
        );
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.delete("/parkinglots/{name}", parkingLot.getName())
        );

        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
