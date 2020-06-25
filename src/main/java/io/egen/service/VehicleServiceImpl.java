package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.entity.Vehicle;
import io.egen.exception.AlertNotFoundException;
import io.egen.exception.VehicleNotFoundException;
import io.egen.repository.AlertsRepository;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private AlertsRepository alertsRepository;

    @Override
    @Transactional
    public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
        vehicles.forEach(vehicle -> vehicleRepository.save(vehicle));
        return vehicles;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getVehicles() {

        return (List<Vehicle>) vehicleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle getVehicle(String vin) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vin);
        if(!vehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with id " + vin + " does not exist");
        }
        return vehicle.get();
    }

    @Override
    @Transactional
    public List<Map<String, String>> getGeoLocation(String vin) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vin);
        if(!vehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with id " + vin + " does not exist");
        }
        Timestamp timestamp = new Timestamp(new Date(System.currentTimeMillis() - 1800 * 1000).getTime());
        List<Object[]> readings = readingsRepository.getGeoLocationReading(vin, timestamp);
        Map<String, String> map = null;
        List<Map<String, String>> geoLocations = new ArrayList<>();
        if(readings != null || readings.size() > 0) {
            for(Object[] reading: readings) {
                map = new HashMap<>();
                map.put("Latitude", reading[0].toString());
                map.put("Longitude", reading[1].toString());
                geoLocations.add(map);
            }
        }
        return geoLocations;
    }

    @Override
    @Transactional
    public List<Alerts> getVehicleAlerts(String vin) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vin);
        if(!vehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with id " + vin + " does not exist");
        }
        List<Alerts> alerts = alertsRepository.findAllByVin(vin);
        if(alerts == null || alerts.size() == 0) {
            throw new AlertNotFoundException("Alerts for vehicle with id " + vin + " does not exist");
        }
        return alerts;
    }
}
