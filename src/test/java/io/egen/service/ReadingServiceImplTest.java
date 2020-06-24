package io.egen.service;

import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.repository.AlertsRepository;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.TiresRpository;
import io.egen.repository.VehicleRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private List<Readings> readingsList;

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
        Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Mockito.when(vehicleRepository.findById("1HGCR2F3XFA027534")).thenReturn(Optional.of(vehicle));

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

        reading.setTires(tires);

        readingsList = Collections.singletonList(reading);

        Mockito.when(readingsRepository.findAll()).thenReturn(readingsList);
        Mockito.when(tiresRpository.save(tires)).thenReturn(tires);
        Mockito.when(readingsRepository.save(reading)).thenReturn(reading);
    }

    @After
    public void cleanup() {

    }

    @Test
    public void getReadings() throws Exception{
        List<Readings> result = readingService.getReadings();
        Assert.assertEquals("List of Readings match", readingsList, result);
    }

    @Test
    public void saveReading() throws Exception {
        Readings result = readingService.saveReading(readingsList.get(0));
        Assert.assertEquals("Reading has been saved successfully", readingsList.get(0), result);
    }
}
