package io.egen.service;

import io.egen.repository.AlertsRepository;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.TiresRpository;
import io.egen.repository.VehicleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ReadingServiceImplTest {

    @TestConfiguration
    static class ReadingServiceImplTestConfiguration {
        @Bean
        public ReadingService getReadingService() {
            return new ReadingServiceImpl();
        }

        @Bean
        public VehicleService getVehicleService() {
            return new VehicleServiceImpl();
        }

        @Bean
        public AlertService getAlertService() {
            return new AlertServiceImpl();
        }
    }

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReadingService readingService;

    @Autowired
    private AlertService alertService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private ReadingsRepository readingsRepository;

    @MockBean
    private TiresRpository tiresRpository;

    @MockBean
    private AlertsRepository alertsRepository;

    @Before
    public void setup() {

    }

    @After
    public void cleanup() {

    }

    @Test
    public void getReadings() throws Exception{

    }

    @Test
    public void saveReading() throws Exception {

    }
}
