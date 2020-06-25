package io.egen.controller;

import io.egen.entity.Alerts;
import io.egen.service.AlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/alerts")
@Api(description = "Alerts generated when rules are triggered")
public class AlertsController {

    @Autowired
    private AlertService alertService;

    @GetMapping
    @ApiOperation(value = "Retrieves all alerts", notes = "Retrieves all alerts generated present in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Alerts> getAlerts() {
        return alertService.getAlerts();
    }

    @GetMapping(value = "/high")
    @ApiOperation(value = "Retrieves HIGH alerts", notes = "Retrieves all HIGH alerts generated in the last 2 hours present in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Alerts> getHighAlerts() {
        return alertService.getHighAlerts();
    }
}
