package com.example.project.partner.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.partner.model.Partner;

public interface PartnerRepository extends JpaRepository<Partner,UUID> {

    boolean existsByCode(String code);
    boolean existsByPhoneNumber1(String phoneNumber1);
    boolean existsByTaxNumber(String taxNumber);
    
    Optional<Partner> findByCode(String code);
} 
