package com.example.project.partner.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.partner.model.Partner;
import com.example.project.partner.repository.PartnerRepository;
import com.example.project.response.ApiResponse;

@Service
public class PartnerService { 
    private final PartnerRepository partnerRepository;   
    public  PartnerService(PartnerRepository partnerRepository){
        this.partnerRepository = partnerRepository;
    }
    public ResponseEntity<ApiResponse<List<Partner>>> getAllPartners(){
        List<Partner>  partners = partnerRepository.findAll();
        if(partners.isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không có dữ liệu", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách thành công", partners));
    }

}
