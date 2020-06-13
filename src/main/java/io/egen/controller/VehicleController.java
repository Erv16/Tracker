package io.egen.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vehicles")
@CrossOrigin(origins = "http://mocker.egen.academy/")
public class VehicleController {
}
