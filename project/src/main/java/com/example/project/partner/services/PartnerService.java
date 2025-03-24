package com.example.project.partner.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.city.model.City;
import com.example.project.partner.model.Partner;
import com.example.project.partner.repository.PartnerRepository;
import com.example.project.partner.util.ExcelHelper;
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
    @Transactional
public void savePartnersFromExcel(MultipartFile file, City city) {
    try {
        List<Partner> partners = ExcelHelper.excelToPartners(file.getInputStream(), city);

        for (Partner partner : partners) {
            if (partner.getId() != null) {
                // Kiểm tra xem partner đã tồn tại chưa
                Optional<Partner> existingPartner = partnerRepository.findById(partner.getId());
                if (existingPartner.isPresent()) {
                    partner = existingPartner.get(); // Nếu tồn tại, lấy dữ liệu từ DB
                }
            } else {
                partner.setId(UUID.randomUUID()); // Tạo ID mới nếu chưa có
            }
        }

        partnerRepository.saveAll(partners);
    } catch (Exception e) {
        throw new RuntimeException("Lỗi khi lưu dữ liệu từ Excel: " + e.getMessage(), e);
    }
}
}
