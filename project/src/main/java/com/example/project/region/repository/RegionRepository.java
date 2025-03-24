package com.example.project.region.repository;

import com.example.project.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
