package io.egen.repository;

import io.egen.entity.Alerts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface AlertsRepository extends CrudRepository<Alerts, String> {

    @Query("SELECT al FROM Alerts al WHERE al.priority = :priority AND al.createdAt >= :timestamp ORDER BY al.createdAt DESC")
    List<Alerts> getHighAlerts(@Param("priority") String priority, @Param("timestamp")Timestamp timestamp);
}
