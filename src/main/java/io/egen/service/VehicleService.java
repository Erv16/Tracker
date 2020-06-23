package io.egen.service;

import io.egen.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> saveVehicles(List<Vehicle> vehicles);

    List<Vehicle> getVehicles();

    Vehicle getVehicle(String vid);
}
