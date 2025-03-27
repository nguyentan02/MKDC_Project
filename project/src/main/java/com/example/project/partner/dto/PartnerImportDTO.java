package com.example.project.partner.dto;

import lombok.Data;
import java.util.Date;
import java.util.UUID;


@Data
public class PartnerImportDTO {
    private String code;
    private String name;
    private String address;
    private String phone_number_1;
    private String phone_number_2;
    private String fax_Number;
    private String owner_name;
    private String tax_Number;
    private UUID cityId;
    private String createdBy;
    private Date createdDate;
    private boolean inUsed;
    private Boolean suspended;
    private Date suspendedStartDate;
    private Date suspendedEndDate;
    private String notes;
    private String email;

}
