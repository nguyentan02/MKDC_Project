package com.example.project.partner.controllers;



import com.example.project.partner.services.ExcelExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/partners")
public class ExportPartnerController {
    private final ExcelExportService excelExportService;

    public ExportPartnerController(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @GetMapping("/export")
    public void exportPartners(HttpServletResponse response) throws IOException {
        excelExportService.exportPartnersToExcel(response);
    }
}

