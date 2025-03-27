package com.example.project.city.model;

import java.util.Date;
import java.util.UUID;

import com.example.project.region.model.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "city")
@Data
public class City {
     @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String code;
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private Date createdDate;

    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Column(nullable = false)
    private boolean inUsed;

    public void setInUsed(char c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInUsed'");
    }
}
