package com.example.project.partner.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.city.model.City;
import com.example.project.city.repository.CityRepository;
import com.example.project.partner.dto.PartnerImportDTO;
import com.example.project.partner.model.Partner;
import com.example.project.partner.repository.PartnerRepository;
import com.example.project.response.ApiResponse;

@Service
public class PartnerService { 
    
    @Autowired
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
    
    @Autowired
    private CityRepository cityRepository;
    @Transactional
    public List<Partner> savePartnersFromExcel(List<PartnerImportDTO> partnerDTOs) {
        List<Partner> partners = partnerDTOs.stream()
            .filter(dto -> !partnerRepository.existsByCode(dto.getCode()))  
            .filter(dto -> !partnerRepository.existsByCode(dto.getPhone_number_1()))  
            .filter(dto -> !partnerRepository.existsByTaxNumber(dto.getTax_Number()))
            .map(dto -> {
                Partner partner = new Partner();
                partner.setCode(dto.getCode());
                partner.setName(dto.getName());
                partner.setAddress(dto.getAddress());
                partner.setPhoneNumber1((dto.getPhone_number_1()));
                partner.setPhone_number_2(dto.getPhone_number_2());
                partner.setFax_Number(dto.getFax_Number());
                partner.setOwner_Name(dto.getOwner_name());
                partner.setTaxNumber(dto.getTax_Number());
              
                Optional<City> cityOpt = cityRepository.findById(dto.getCityId());
                partner.setCity(cityOpt.orElse(null));

                partner.setCreated_By(dto.getCreatedBy());
                partner.setCreated_Date(dto.getCreatedDate());
                partner.setIn_Used(dto.isInUsed());
                partner.setSuspended(dto.getSuspended());
                partner.setSuspended_Start_Date(dto.getSuspendedStartDate());
                partner.setSuspended_End_Date(dto.getSuspendedEndDate());
                partner.setNotes(dto.getNotes());
                partner.setEmail(dto.getEmail());

                return partner;
            }).collect(Collectors.toList());

        return partnerRepository.saveAll(partners);
    }
}
        
    

