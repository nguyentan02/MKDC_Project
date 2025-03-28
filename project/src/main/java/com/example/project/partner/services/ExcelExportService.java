package com.example.project.partner.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.example.project.partner.model.Partner;
import com.example.project.partner.repository.PartnerRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {
    private final PartnerRepository partnerRepository;

    public ExcelExportService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void exportPartnersToExcel(HttpServletResponse response) throws IOException {
        List<Partner> partners = partnerRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Partners");

            // Tạo dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                "Mã công ty", "Tên công ty", "Địa chỉ", "Số điện thoại 1", "Số điện thoại 2",
                "Mã số thuế", "Chủ sở hữu", "Số fax", "Tên thành phố",
                "Người tạo", "Đang hoạt động", "Tạm ngừng",
                "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú", "Email"
            };

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Ghi dữ liệu vào các dòng
            int rowIdx = 1;
            for (Partner partner : partners) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(partner.getCode());
                row.createCell(1).setCellValue(partner.getName());
                row.createCell(2).setCellValue(partner.getAddress());
                row.createCell(3).setCellValue(partner.getPhoneNumber1());
                row.createCell(4).setCellValue(partner.getPhone_number_2());
                row.createCell(5).setCellValue(partner.getTaxNumber());
                row.createCell(6).setCellValue(partner.getOwner_Name());
                row.createCell(7).setCellValue(partner.getFax_Number());
                row.createCell(8).setCellValue(partner.getCity() != null ? partner.getCity().getName() : ""); // Lấy tên thành phố
                row.createCell(9).setCellValue(partner.getCreated_By());
                row.createCell(10).setCellValue(partner.isIn_Used() ? "Có" : "Không");
                row.createCell(11).setCellValue(partner.getSuspended() ? "Có" : "Không");
                row.createCell(12).setCellValue(partner.getSuspended_Start_Date() != null ? partner.getSuspended_Start_Date().toString() : "");
                row.createCell(13).setCellValue(partner.getSuspended_End_Date() != null ? partner.getSuspended_End_Date().toString() : "");
                row.createCell(14).setCellValue(partner.getNotes());
                row.createCell(15).setCellValue(partner.getEmail());
            }

            // Xuất file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=partners.xlsx");

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
        }
    }
}
