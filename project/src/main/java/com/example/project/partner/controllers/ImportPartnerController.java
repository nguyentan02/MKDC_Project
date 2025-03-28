package com.example.project.partner.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.partner.dto.PartnerImportDTO;
import com.example.project.partner.model.Partner;
import com.example.project.partner.repository.PartnerRepository;
import com.example.project.partner.services.ExcelService;
import com.example.project.partner.services.PartnerService;


import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/partners")
public class ImportPartnerController {
     @Autowired
    private ExcelService excelService;

    @Autowired
    private PartnerService partnerService;
    private final PartnerRepository partnerRepository;
    public ImportPartnerController(PartnerService partnerService, PartnerRepository partnerRepository){
            this.partnerService = partnerService;
            this.partnerRepository = partnerRepository;
    }

@PostMapping("/import")
public ResponseEntity<?> importPartners(@RequestParam("file") MultipartFile file) {
   
    try {
        List<PartnerImportDTO> partnerDTOs = excelService.parseExcelFile(file);
        
        
        List<String> duplicateCodes = partnerDTOs.stream()
                .map(PartnerImportDTO::getCode)
                .filter(code -> partnerRepository.existsByCode(code))
                .collect(Collectors.toList());

        if (!duplicateCodes.isEmpty()) {
            return ResponseEntity.badRequest().body("Lỗi: Các đối tác sau đã tồn tại: " + duplicateCodes);
        }

        List<Partner> savedPartners = partnerService.savePartnersFromExcel(partnerDTOs);
        return ResponseEntity.ok(savedPartners);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Lỗi khi import file: " + e.getMessage());
    }
}

}
