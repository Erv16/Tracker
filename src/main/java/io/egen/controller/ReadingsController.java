package io.egen.controller;

import io.egen.entity.Readings;
import io.egen.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/readings")
@CrossOrigin(origins = "http://mocker.egen.academy")
public class ReadingsController {

    @Autowired
    private ReadingService readingService;

    @GetMapping
    public List<Readings> getReadings() {
        return readingService.getReadings();
    }

    @PostMapping
    public Readings saveReading(@RequestBody Readings reading) {
        return readingService.saveReading(reading);
    }

}
