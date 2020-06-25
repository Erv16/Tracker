package io.egen.controller;

import io.egen.entity.Alerts;
import io.egen.entity.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleControllerTest {
    @Before
    public void setup() {

    }

    @After
    public void cleanup() {

    }

    @Test
    public void saveVehicles() throws Exception {

    }

    @Test
    public void getVehicles() throws Exception {

    }

    @Test
    public void getVehicle() throws Exception {

    }

    @Test
    public void getGeoLocations() throws Exception {

    }

    @Test
    public void getVehicleAlerts() throws Exception {

    }
}
