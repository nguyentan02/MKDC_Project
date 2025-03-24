package com.example.project.partner.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.project.city.model.City;
import com.example.project.partner.model.Partner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelHelper {
    public static List<Partner> excelToPartners(InputStream is, City existingCity) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Partner> partners = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề

                Partner partner = new Partner();
                partner.setId(UUID.randomUUID()); // Tạo UUID mới
                
                partner.setCode(getCellValue(row.getCell(0)));
                partner.setName(getCellValue(row.getCell(1)));
                partner.setAddress(getCellValue(row.getCell(2)));
                partner.setPhone_number_1(getCellValue(row.getCell(3)));
                partner.setPhone_number_2(getCellValue(row.getCell(4)));
                partner.setOwner_Name(getCellValue(row.getCell(5)));
                partner.setTax_Number(getCellValue(row.getCell(6)));
                partner.setCreated_By("admin");
                partner.setCreated_Date(new Date());
                partner.setIn_Used(true);

                // Kiểm tra nếu City có tồn tại trước khi gán vào Partner
                if (existingCity != null && existingCity.getId() != null) {
                    partner.setCity(existingCity);
                } else {
                    throw new RuntimeException("City không tồn tại trong database!");
                }

                partners.add(partner);
            }
            workbook.close();
            return partners;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi đọc file Excel: " + e.getMessage(), e);
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return ""; // Trả về chuỗi rỗng nếu ô bị null
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue()); // Chuyển số thành chuỗi
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
