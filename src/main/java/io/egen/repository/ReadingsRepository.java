package io.egen.repository;

import io.egen.entity.Readings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ReadingsRepository extends CrudRepository<Readings, String> {

    @Query("SELECT rd FROM Readings rd WHERE rd.vin = :vin AND rd.createdAt >= :timestamp")
    List<Readings> getGeoLocationReading(@Param("vin") String vin, @Param("timestamp")Timestamp timestamp);

}
