package io.egen.repository;

import io.egen.entity.Readings;
import org.springframework.data.repository.CrudRepository;

public interface ReadingsRepository extends CrudRepository<Readings, String> {
}
