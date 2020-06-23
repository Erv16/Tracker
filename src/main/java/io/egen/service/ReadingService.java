package io.egen.service;

import io.egen.entity.Readings;

import java.util.List;

public interface ReadingService {
    List<Readings> getReadings();
    Readings saveReading(Readings reading);
}
