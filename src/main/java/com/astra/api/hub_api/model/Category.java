package com.astra.api.hub_api.model;

import java.util.List;

import com.astra.api.hub_api.emodel.StructureDenominator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category{
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "structure_denominator")
    @Enumerated(EnumType.STRING)
    private StructureDenominator structureDenominator;

    @Column
    private String label;

    @Column
    private List<String> type;
}