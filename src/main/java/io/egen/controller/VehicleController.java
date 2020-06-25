package io.egen.controller;

import io.egen.entity.Alerts;
import io.egen.entity.Vehicle;
import io.egen.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/vehicles")
@CrossOrigin(origins = "http://mocker.egen.academy")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PutMapping
    public List<Vehicle> saveVehicles(@RequestBody List<Vehicle> vehicles) {
        return vehicleService.saveVehicles(vehicles);
    }

    @GetMapping
    public List<Vehicle> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping(value = "/{vehicleId}")
    public Vehicle getVehicle(@PathVariable("vehicleId") String vid) {
        return vehicleService.getVehicle(vid);
    }

    @GetMapping(value = "/location/{vehicleId}")
    public List<Map<String,String>> getGeoLocations(@PathVariable("vehicleId") String vin) {
        return vehicleService.getGeoLocation(vin);
    }

    @GetMapping(value = "/alerts/{vehicleId}")
    public List<Alerts> getVehicleAlerts(@PathVariable("vehicleId") String vin) {
        return vehicleService.getVehicleAlerts(vin);
    }
}
