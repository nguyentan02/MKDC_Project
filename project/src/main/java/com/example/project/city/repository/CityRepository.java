package com.example.project.city.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.city.model.City;

public interface CityRepository extends JpaRepository<City,UUID> {
    Optional<City> findByName(String name);
     
}
