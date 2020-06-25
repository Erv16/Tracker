package io.egen.controller;

import io.egen.entity.Alerts;
import io.egen.entity.Vehicle;
import io.egen.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/vehicles")
@CrossOrigin(origins = "http://mocker.egen.academy")
@Api(description = "Vehicles related REST API calls")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PutMapping
    @ApiOperation(value = "Creates or Updates vehicle details", notes = "Returns a list of all the vehicles that have been created/updated in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Vehicle> saveVehicles(@RequestBody List<Vehicle> vehicles) {
        return vehicleService.saveVehicles(vehicles);
    }

    @GetMapping
    @ApiOperation(value = "Retrieves all vehicles details", notes = "Returns a detailed list of all the vehicles that are available in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Vehicle> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping(value = "/{vehicleId}")
    @ApiOperation(value = "Retrieves a particular vehicle's details", notes = "Returns the specified vehicle's details that is available in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Vehicle getVehicle(@PathVariable("vehicleId") String vid) {
        return vehicleService.getVehicle(vid);
    }

    @GetMapping(value = "/location/{vehicleId}")
    @ApiOperation(value = "Retrieves a vehicle's geo location co-ordinates", notes = "Returns a list of the specified vehicle's geo location co-ordinates recorded within the last 30mins that are available in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Map<String,String>> getGeoLocations(@PathVariable("vehicleId") String vin) {
        return vehicleService.getGeoLocation(vin);
    }

    @GetMapping(value = "/alerts/{vehicleId}")
    @ApiOperation(value = "Retrieves a vehicle's historical alerts", notes = "Returns a list of all the alerts generated for a specified vehicle that are available in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Alerts> getVehicleAlerts(@PathVariable("vehicleId") String vin) {
        return vehicleService.getVehicleAlerts(vin);
    }
}
