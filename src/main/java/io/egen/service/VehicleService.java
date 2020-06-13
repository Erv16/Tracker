package io.egen.service;

import io.egen.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> saveVehicle(List<Vehicle> vehicles);

    List<Vehicle> getVehicles();
}
