package io.egen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.repository.AlertsRepository;
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
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AlertsRepository alertsRepository;

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

        Alerts alert = new Alerts();
        alert.setVin("1HGCR2F3XFA027534");
        alert.setRule("Vehicle " + vehicle.getMake() + " current fuel volume <" + reading.getFuelVolume() + "> is less than 10% of the maximum fuel volume <" + vehicle.getMaxFuelVolume() + ">");
        alert.setPriority("MEDIUM");
        alert.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        alertsRepository.save(alert);
    }

    @After
    public void cleanup() {
        vehicleRepository.deleteAll();
        tiresRpository.deleteAll();
        readingsRepository.deleteAll();
        alertsRepository.deleteAll();
    }

    @Test
    public void getVehicles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void saveVehicles() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Vehicle v = new Vehicle();
        v.setVin("WP1AB29P63LA60179");
        v.setMake("PORSCHE");
        v.setModel("CAYENNE");
        v.setYear("2015");
        v.setRedlineRpm(8000.0);
        v.setMaxFuelVolume(18.0);
        v.setLastServiceDate(new Timestamp(System.currentTimeMillis()));
        List<Vehicle> vehicleList = Collections.singletonList(v);

        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(vehicleList)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("WP1AB29P63LA60179")));

        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void getVehicle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")));

    }

    @Test
    public void getGeoLocations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/location/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Latitude", Matchers.is("41.803194")));
    }

    @Test
    public void getVehicleAlerts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/alerts/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void getVehicle404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/gibberish"))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }

    @Test
    public void getGeoLocations404() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/location/gibberish"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getVehicleAlerts404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/alerts/gibberish"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
