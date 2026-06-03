package com.mastertbal.csvbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportSummary {
    private int totalRows;
    private int imported;
    private int failed;
    private List<Failure> failures;
}
