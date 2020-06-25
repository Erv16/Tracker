package io.egen.controller;

import io.egen.entity.Readings;
import io.egen.service.ReadingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/readings")
@CrossOrigin(origins = "http://mocker.egen.academy")
@Api(description = "Vehicle Readings related REST API calls")
public class ReadingsController {

    @Autowired
    private ReadingService readingService;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Retrieves all recorded readings", notes = "Returns a list of all readings recorded from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Readings> getReadings() {
        return readingService.getReadings();
    }

    @PostMapping
    @ApiOperation(value = "Records a generated reading", notes = "Returns the record saved in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Readings saveReading(@RequestBody Readings reading) {
        return readingService.saveReading(reading);
    }

}
