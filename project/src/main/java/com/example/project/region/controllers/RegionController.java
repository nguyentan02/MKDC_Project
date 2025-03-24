package com.example.project.region.controllers;
import org.springframework.web.bind.annotation.*;

import com.example.project.region.model.Region;
import com.example.project.region.services.RegionService;
import com.example.project.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Region>>> getAllRegions() {
        return regionService.getAllRegions();
    }
}
