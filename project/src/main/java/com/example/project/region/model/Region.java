package com.example.project.region.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "region")
@Data
public class Region {
    @Id
    @GeneratedValue
    private UUID id;
      @Column(nullable = false)
    private String code;

    private String name;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private Date createdDate;

    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Column(nullable = false)
    private boolean inUsed;
}
