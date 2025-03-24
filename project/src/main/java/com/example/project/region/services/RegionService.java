package com.example.project.region.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.region.model.Region;
import com.example.project.region.repository.RegionRepository;
import com.example.project.response.ApiResponse;

@Service
public class RegionService { 
    private final RegionRepository regionRepository;   
    public  RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }
    public ResponseEntity<ApiResponse<List<Region>>> getAllRegions(){
        List<Region>  regions = regionRepository.findAll();
        if(regions.isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không có dữ liệu vùng", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách vùng thành công", regions));
    }

}
