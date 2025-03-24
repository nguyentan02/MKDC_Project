package com.example.project.partner.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.partner.model.Partner;
import com.example.project.partner.services.PartnerService;
import com.example.project.response.ApiResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/partners")
public class PartnerController {
    private final PartnerService partnerService;
    public PartnerController(PartnerService partnerService){
        this.partnerService = partnerService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Partner>>> getAllPartners(){
        return partnerService.getAllPartners();
    }
}
