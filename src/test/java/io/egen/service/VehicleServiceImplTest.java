package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.repository.AlertsRepository;
import io.egen.repository.ReadingsRepository;
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
import java.util.*;

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

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private ReadingsRepository readingsRepository;

    @MockBean
    private AlertsRepository alertsRepository;

    private List<Vehicle> vehiclesList;

    private List<Readings> readingsList;

    private List<Alerts> alertsList;

    private List<Readings> geoLocation;

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

        vehiclesList = Collections.singletonList(vehicle);

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

        Alerts alert = new Alerts();
        alert.setVin("1HGCR2F3XFA027534");
        alert.setRule("Vehicle " + vehicle.getMake() + " current fuel volume <" + reading.getFuelVolume() + "> is less than 10% of the maximum fuel volume <" + vehicle.getMaxFuelVolume() + ">");
        alert.setPriority("MEDIUM");
        alert.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        alertsList = Collections.singletonList(alert);

        //geoLocation.add(readingsList.get(0));

        Mockito.when(vehicleRepository.findAll()).thenReturn(vehiclesList);

        Mockito.when(vehicleRepository.findById("1HGCR2F3XFA027534")).thenReturn(Optional.of(vehicle));

        Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Mockito.when(alertsRepository.findAllByVin("1HGCR2F3XFA027534")).thenReturn(alertsList);

        Mockito.when(readingsRepository.getGeoLocationReading("1HGCR2F3XFA027534", new Timestamp(new Date(System.currentTimeMillis() - 1800 * 1000).getTime()))).thenReturn(readingsList);
    }

    @After
    public void cleanup() {

    }

    @Test
    public void getVehicles() throws Exception {
        List<Vehicle> result = vehicleService.getVehicles();
        Assert.assertEquals("List of vehicles returned should match", vehiclesList, result);
    }

    @Test
    public void saveVehicle() throws Exception {
        List<Vehicle> result = vehicleService.saveVehicles(vehiclesList);
        Assert.assertEquals("Vehicle has been created successfully", vehiclesList, result);
    }

    @Test
    public void getVehicle() throws Exception {
        Vehicle result = vehicleService.getVehicle("1HGCR2F3XFA027534");
        Assert.assertEquals("Vehicle details should match", vehiclesList.get(0), result);
    }

    @Test
    public void getVehicleAlerts() throws Exception {
        List<Alerts> result = vehicleService.getVehicleAlerts("1HGCR2F3XFA027534");
        Assert.assertEquals("List of alerts returned should matched", alertsList, result);
    }

//    @Test
//    public void getGeoLocation() throws Exception {
//        List<Readings> result = readingsRepository.getGeoLocationReading("1HGCR2F3XFA027534", new Timestamp(new Date(System.currentTimeMillis() - 1800 * 1000).getTime()));
//        System.out.println(">>>>>" + result.toString());
//        Assert.assertEquals("The readings returned should match", readingsList, result);
//    }
}
