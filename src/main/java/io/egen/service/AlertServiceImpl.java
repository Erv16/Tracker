package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.entity.Vehicle;
import io.egen.repository.AlertsRepository;
import io.egen.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AlertServiceImpl implements AlertService{

    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Alerts> getAlerts() {
        return (List<Alerts>) alertsRepository.findAll();
    }

    @Override
    public void generateAlert(String vin, String rule, String priority) {
        Alerts alert = new Alerts();
        alert.setVin(vin);
        alert.setRule(rule);
        alert.setPriority(priority);
        alert.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        alertsRepository.save(alert);
    }

    @Override
    public void ruleCheckToGenerateAlert(Readings reading) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(reading.getVin());
        String rule = "";
        if (!vehicle.isPresent()) {
            throw new NoSuchElementException("Vehicle with id " + vehicle.get().getVin() + " does not exist");
        } else {
            if (reading.getEngineRpm() > vehicle.get().getRedlineRpm()) {
                rule = "Vehicle " + vehicle.get().getModel() + " Engine RPM <" + reading.getEngineRpm() + "> is greater than the Redline RPM <" + vehicle.get().getRedlineRpm() + "> specified";
                generateAlert(vehicle.get().getVin(), rule, "HIGH");
            }
            if (reading.getFuelVolume() < (0.10 * vehicle.get().getMaxFuelVolume())) {
                rule = "Vehicle " + vehicle.get().getMake() + " current fuel volume <" + reading.getFuelVolume() + "> is less than 10% of the maximum fuel volume <" + vehicle.get().getMaxFuelVolume() + ">";
                generateAlert(vehicle.get().getVin(), rule, "MEDIUM");
            }
            if (reading.getTires().getFrontLeft() > 36 || reading.getTires().getFrontLeft() < 32) {
                rule = "Front Left tire pressure is : <" + reading.getTires().getFrontLeft() + "> psi";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
            if (reading.getTires().getRearLeft() > 36 || reading.getTires().getRearLeft() < 32) {
                rule = "Rear Left tire pressure is : <" + reading.getTires().getRearLeft() + "> psi";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
            if (reading.getTires().getFrontRight() > 36 || reading.getTires().getFrontRight() < 32) {
                rule = "Front Right tire pressure is : <" + reading.getTires().getFrontRight() + "> psi";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
            if (reading.getTires().getRearRight() > 36 || reading.getTires().getRearRight() < 32) {
                rule = "Rear Right tire pressure is : <" + reading.getTires().getRearRight() + "> psi";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
            if (reading.isEngineCoolantLow()) {
                rule = "Engine Coolant is running low";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
            if (reading.isCheckEngineLightOn()) {
                rule = "Check Engine Light is On";
                generateAlert(vehicle.get().getVin(), rule, "LOW");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerts> getHighAlerts() {
        Timestamp timestamp = new Timestamp(new Date(System.currentTimeMillis() - 7200 * 1000).getTime());
        return alertsRepository.getHighAlerts("HIGH", timestamp);
    }
}
