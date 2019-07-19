package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ParkingLotControllerTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

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

        result.andExpect(status().isOk())
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

        result.andExpect(status().isNoContent());
    }

    @Test
    public void should_return_is_page_with_parkingLot_when_search_parking_lot_() throws Exception{
        //given
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setCapacity(16);
        parkingLot.setName("parkinglot1");
        parkingLot.setLocation("address1");
        ParkingLot parkingLot1=new ParkingLot();
        parkingLot1.setCapacity(16);
        parkingLot1.setName("parkinglot2");
        parkingLot1.setLocation("address2");
        parkingLotRepository.save(parkingLot);
        parkingLotRepository.save(parkingLot1);

        //when
        String result= this.mvc.perform(get("/parkinglots/").param("page","1").param("pageSize","15")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        //then
        JSONArray jsonArray = new JSONArray(result);
        Assertions.assertEquals("parkinglot1",jsonArray.getJSONObject(0).getString("name"));
        Assertions.assertEquals("parkinglot2",jsonArray.getJSONObject(1).getString("name"));

    }

    @Test
    public void should_return_parkinglot_when_search_parking_lot_by_id() throws Exception{
        //given
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setCapacity(16);
        parkingLot.setName("parkinglot1");
        parkingLot.setLocation("address1");
        ParkingLot parkingLot1=parkingLotRepository.save(parkingLot);
        //when

        String result=this.mvc.perform(get("/parkinglots/"+parkingLot1.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        //then
        JSONObject jsonObject=new JSONObject(result);
        Assertions.assertEquals("parkinglot1",jsonObject.getString("name"));

    }

    @Test
    public void should_return_parkinglot_when_put_parkinglot() throws Exception{
        //given
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setCapacity(16);
        parkingLot.setName("parkinglot1");
        parkingLot.setLocation("address1");
        ParkingLot parkingLot1=parkingLotRepository.save(parkingLot);
        //when
        parkingLot.setName("pa1");
        JSONObject jsonObject = new JSONObject(parkingLot);

        String result=this.mvc.perform(put("/parkinglots").content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        //then
        JSONObject jsonObject1=new JSONObject(result);
        Assertions.assertEquals("pa1",jsonObject.getString("name"));

    }
    }
}
