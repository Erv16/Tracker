package io.egen.controller;

import io.egen.repository.ReadingsRepository;
import io.egen.repository.TiresRpository;
import io.egen.repository.VehicleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    }

    @After
    public void cleanup() {
        vehicleRepository.deleteAll();
        readingsRepository.deleteAll();
        tiresRpository.deleteAll();
    }

    @Test
    public void getReadings() throws Exception {

    }

    @Test
    public void saveReading() throws Exception {

    }
}
