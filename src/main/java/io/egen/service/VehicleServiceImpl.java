package io.egen.service;

import io.egen.entity.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Override
    @Transactional
    public List<Vehicle> saveVehicle(List<Vehicle> vehicles) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getVehicles() {
        return null;
    }
}
