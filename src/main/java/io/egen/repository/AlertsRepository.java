package io.egen.repository;

import io.egen.entity.Alerts;
import org.springframework.data.repository.CrudRepository;

public interface AlertsRepository extends CrudRepository<Alerts, String> {
}
