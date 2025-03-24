package com.example.project.partner.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

import com.example.project.city.model.City;

@Entity
@Table(name = "partner")
@Data
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(name = "name", columnDefinition = "NVARCHAR(1023)")
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone_number_1;

    private String phone_number_2;
    private String fax_Number;

    @Column(nullable = false)
    private String owner_Name;

    @Column(nullable = false)
    private String tax_Number;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(nullable = false)
    private String created_By;

    @Column(nullable = false)
    private Date created_Date;

    private String last_Modified_By;
    private Date last_Modified_Date;

    @Column(nullable = false)
    private boolean in_Used;

    private Boolean suspended;
    private Date suspended_Start_Date;
    private Date suspended_End_Date;
    private String notes;
    private String email;
}
