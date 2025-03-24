package com.example.project.partner.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.city.model.City;
import com.example.project.partner.services.PartnerService;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/partners")
public class ImportPartnerController {
    private final PartnerService partnerService;
    
    public ImportPartnerController(PartnerService partnerService){
        this.partnerService = partnerService;
    }
    @PostMapping("/import")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Vui lòng chọn file Excel");
        }
        City defaultCity = new City();
        defaultCity.setId(UUID.fromString("34b1c1f2-f50c-483a-89e0-9a5657c33c7f"));
        partnerService.savePartnersFromExcel(file, defaultCity);
        return ResponseEntity.ok("Import thành công");
    }
    
}
