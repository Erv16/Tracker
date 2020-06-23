package io.egen.service;

import io.egen.entity.Vehicle;
import io.egen.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

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
    public Vehicle getVehicle(String vid) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vid);
        if(!vehicle.isPresent()) {
            throw new NoSuchElementException("Vehicle with id " + vid + " does not exist");
        }
        return vehicle.get();
    }
}
