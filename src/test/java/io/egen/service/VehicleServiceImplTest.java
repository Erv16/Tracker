package io.egen.service;

import io.egen.repository.AlertsRepository;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.VehicleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfiguration {
        @Bean
        public VehicleService getService() {
            return new VehicleServiceImpl();
        }
    }

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private AlertsRepository alertsRepository;

    @Before
    public void setup() {

    }

    @After
    public void cleanup() {

    }
}
