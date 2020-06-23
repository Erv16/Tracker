package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.exception.VehicleNotFoundException;
import io.egen.repository.ReadingsRepository;
import io.egen.repository.TiresRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReadingServiceImpl implements ReadingService{

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private TiresRpository tiresRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AlertService alertService;

    @Override
    @Transactional
    public List<Readings> getReadings() {
        return (List<Readings>) readingsRepository.findAll();
    }

    @Override
    @Transactional
    public Readings saveReading(Readings reading) {
        Optional<Vehicle> vehicle = Optional.ofNullable(vehicleService.getVehicle(reading.getVin()));
        if(!vehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with id " + vehicle.get().getVin() + " does not exist");
        }
        reading.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Tires tires = reading.getTires();
        alertService.ruleCheckToGenerateAlert(reading);
        tiresRepository.save(tires);
        return readingsRepository.save(reading);
    }
}
