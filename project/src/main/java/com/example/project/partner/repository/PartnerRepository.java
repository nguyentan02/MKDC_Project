package com.example.project.partner.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.partner.model.Partner;

public interface PartnerRepository extends JpaRepository<Partner,UUID> {

    
} 
