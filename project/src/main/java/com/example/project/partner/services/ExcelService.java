package com.example.project.partner.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.city.model.City;
import com.example.project.city.repository.CityRepository;
import com.example.project.partner.dto.PartnerImportDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.nio.charset.StandardCharsets;
@Service
public class ExcelService {
    private final CityRepository cityRepository;

    public ExcelService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<PartnerImportDTO> parseExcelFile(MultipartFile file) throws IOException {
        List<PartnerImportDTO> partners = new ArrayList<>();
        // List<String> errors = new ArrayList<>(); 
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new RuntimeException("Lỗi: Không tìm thấy sheet trong file Excel.");
            }

            // Đọc dòng tiêu đề
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Lỗi: File Excel không có tiêu đề.");
            }

            // Ánh xạ tên cột → vị trí cột
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
            }

            // Danh sách cột bắt buộc
            List<String> requiredColumns = Arrays.asList(
                "Mã công ty", "Tên công ty", "Địa chỉ", "Số điện thoại 1", "Số điện thoại 2",
                "Mã số thuế", "Chủ sở hữu", "Số fax", "Tên thành phố",
                "Người tạo", "Đang hoạt động", "Tạm ngừng",
                "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú", "Email"
            );

            // Kiểm tra xem các cột có đủ không
            for (String col : requiredColumns) {
                if (!columnIndexMap.containsKey(col)) {
                    throw new RuntimeException("Thiếu cột bắt buộc: " + col);
                }
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                Row row = sheet.getRow(i);
                if (row == null) continue;
                    PartnerImportDTO partner = new PartnerImportDTO();
                    partner.setCode(getCellValueAsString(row.getCell(columnIndexMap.get("Mã công ty"))));
                    partner.setName(getCellValueAsString(row.getCell(columnIndexMap.get("Tên công ty"))));
                    partner.setAddress(getCellValueAsString(row.getCell(columnIndexMap.get("Địa chỉ"))));
                    partner.setPhone_number_1(getCellValueAsString(row.getCell(columnIndexMap.get("Số điện thoại 1"))));
                    partner.setPhone_number_2(getCellValueAsString(row.getCell(columnIndexMap.get("Số điện thoại 2"))));
                    partner.setTax_Number(getCellValueAsString(row.getCell(columnIndexMap.get("Mã số thuế"))));
                    partner.setOwner_name(getCellValueAsString(row.getCell(columnIndexMap.get("Chủ sở hữu"))));
                    partner.setFax_Number(getCellValueAsString(row.getCell(columnIndexMap.get("Số fax"))));
    
                    // Xử lý Tên thành phố -> ID
                    String cityName = getCellValueAsString(row.getCell(columnIndexMap.get("Tên thành phố")));
                    if (!cityName.isEmpty()) {
                        Optional<City> cityOptional = cityRepository.findByName(cityName);
                        if (cityOptional.isPresent()) {
                            partner.setCityId(cityOptional.get().getId());
                        } else {
                            throw new RuntimeException("Không tìm thấy thành phố: " + cityName);
                        }
                    } else {
                        partner.setCityId(null);
                    }
    
                    partner.setCreatedBy(getCellValueAsString(row.getCell(columnIndexMap.get("Người tạo"))));
                    partner.setCreatedDate(new Date());
                    partner.setInUsed(getCellValueAsBoolean(row.getCell(columnIndexMap.get("Đang hoạt động"))));
                    partner.setSuspended(getCellValueAsBoolean(row.getCell(columnIndexMap.get("Tạm ngừng"))));
                    partner.setSuspendedStartDate(getCellValueAsDate(row.getCell(columnIndexMap.get("Ngày bắt đầu"))));
                    partner.setSuspendedEndDate(getCellValueAsDate(row.getCell(columnIndexMap.get("Ngày kết thúc"))));
                    partner.setNotes(getCellValueAsString(row.getCell(columnIndexMap.get("Ghi chú"))));
                    partner.setEmail(getCellValueAsString(row.getCell(columnIndexMap.get("Email"))));
                    partners.add(partner);
            }
        }
        return partners;
    }

    // Lấy danh sách thành phố từ DB (tối ưu hiệu suất)
//     private Map<String, UUID> getCityMap() {
//         List<City> cities = cityRepository.findAll();
//         Map<String, UUID> cityMap = new HashMap<>();
//         for (City city : cities) {
//             cityMap.put(city.getName(), city.getId());
//         }
//         return cityMap;
//     }
// private String removeDiacritics(String input) {
//     if (input == null) return "";
//     String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
//     return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
// }
   

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return new String(cell.getStringCellValue().trim().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }
    
    // Chuyển đổi ô thành Boolean
    private Boolean getCellValueAsBoolean(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                String value = cell.getStringCellValue().trim().toLowerCase();
                return "true".equals(value) || "1".equals(value);
            case NUMERIC:
                return cell.getNumericCellValue() != 0;
            default:
                return null;
        }
    }

    // Chuyển đổi ô thành Date
    private Date getCellValueAsDate(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            return null;
        }
        return cell.getDateCellValue();
    }
}
