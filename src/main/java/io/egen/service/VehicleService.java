package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Vehicle;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface VehicleService {

    List<Vehicle> saveVehicles(List<Vehicle> vehicles);

    List<Vehicle> getVehicles();

    Vehicle getVehicle(String vin);

    List<Map<String, String>> getGeoLocation(String vin);

    List<Alerts> getVehicleAlerts(String vin);
}
