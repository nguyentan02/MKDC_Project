package com.example.project.city.controllers;
import org.springframework.web.bind.annotation.*;

import com.example.project.city.model.City;
import com.example.project.city.services.CityService;
import com.example.project.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<City>>> getAllCitys() {
        return cityService.getAllCitys();
    }
}
