package com.example.project.city.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.city.model.City;
import com.example.project.city.repository.CityRepository;
import com.example.project.response.ApiResponse;
@Service
public class CityService {
        private final CityRepository cityRepository;
        public CityService(CityRepository cityRepository){
            this.cityRepository = cityRepository;
        }

        public ResponseEntity<ApiResponse<List<City>>> getAllCitys(){
            List<City> citys = cityRepository.findAll();
            if(citys.isEmpty()){
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không có dữ liệu thành phố", null));

            }
            return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách thành phố thành công", citys));
        }
}
