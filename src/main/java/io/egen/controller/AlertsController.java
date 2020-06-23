package io.egen.controller;

import io.egen.entity.Alerts;
import io.egen.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/alerts")
public class AlertsController {

    @Autowired
    private AlertService alertService;

    @GetMapping
    public List<Alerts> getAlerts() {
        return alertService.getAlerts();
    }

    @GetMapping(value = "/high")
    public List<Alerts> getHighAlerts() {
        return alertService.getHighAlerts();
    }
}
