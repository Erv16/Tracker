package io.egen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.TiresRpository;
import io.egen.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class ReadingsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private TiresRpository tiresRpository;

    @Before
    public void setup() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin("1HGCR2F3XFA027534");
        vehicle.setMake("HONDA");
        vehicle.setModel("ACCORD");
        vehicle.setYear("2015");
        vehicle.setRedlineRpm(5500.0);
        vehicle.setMaxFuelVolume(15.0);
        vehicle.setLastServiceDate(new Timestamp(System.currentTimeMillis()));
        vehicleRepository.save(vehicle);

        Readings reading = new Readings();
        reading.setVin("1HGCR2F3XFA027534");
        reading.setLatitude(41.803194);
        reading.setLongitude(-88.144406);
        reading.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reading.setFuelVolume(1.2);
        reading.setSpeed(85.0);
        reading.setEngineHp(240.0);
        reading.setCheckEngineLightOn(false);
        reading.setEngineCoolantLow(false);
        reading.setCruiseControlOn(true);
        reading.setEngineRpm(5300.0);

        Tires tires = new Tires();
        tires.setRearRight(35);
        tires.setRearLeft(33);
        tires.setFrontRight(32);
        tires.setFrontLeft(31);

        tiresRpository.save(tires);
        reading.setTires(tires);
        readingsRepository.save(reading);

    }

    @After
    public void cleanup() {
        vehicleRepository.deleteAll();
        readingsRepository.deleteAll();
        tiresRpository.deleteAll();
    }

    @Test
    public void getReadings() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void saveReading() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Readings rd = new Readings();
        rd.setVin("1HGCR2F3XFA027534");
        rd.setLatitude(35.803194);
        rd.setLongitude(-68.144406);
        rd.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        rd.setFuelVolume(1.5);
        rd.setSpeed(85.0);
        rd.setEngineHp(240.0);
        rd.setCheckEngineLightOn(false);
        rd.setEngineCoolantLow(false);
        rd.setCruiseControlOn(true);
        rd.setEngineRpm(3300.0);

        Tires t = new Tires();
        t.setRearRight(35);
        t.setRearLeft(33);
        t.setFrontRight(32);
        t.setFrontLeft(31);

        rd.setTires(t);

        mvc.perform(MockMvcRequestBuilders.post("/readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(rd)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")));

        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
}
