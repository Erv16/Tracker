package io.egen.service;

import io.egen.entity.Alerts;
import io.egen.entity.Readings;
import io.egen.repository.AlertsRepository;

import java.sql.Timestamp;
import java.util.List;

public interface AlertService {
    List<Alerts> getAlerts();
    void generateAlert(String vin, String rule, String priority);
    void ruleCheckToGenerateAlert(Readings reading);
    List<Alerts> getHighAlerts();
}
