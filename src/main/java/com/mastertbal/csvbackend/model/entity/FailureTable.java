package com.mastertbal.csvbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "failure_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FailureTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "csv_row")
    private int csvRow;

    @Column(name = "csv_column")
    private String csvColumn;

    @Column(name = "reason")
    private String reason;
}
