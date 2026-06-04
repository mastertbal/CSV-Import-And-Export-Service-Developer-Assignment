package com.mastertbal.csvbackend.service.impl;

import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.entity.FailureTable;
import com.mastertbal.csvbackend.repository.FailureTableRepository;
import com.mastertbal.csvbackend.service.FailureTableService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FailureTableImpl implements FailureTableService {

    private final FailureTableRepository failureTableRepository;

    @Override
    public FailureTable saveFailure(FailureTable failureTable) {
        return failureTableRepository.save(failureTable);
    }

    @Override
    public void getAllFailures(HttpServletResponse response) throws Exception{
        List<FailureTable> failureTables = failureTableRepository.findAll();
        if (failureTables.isEmpty()) throw new RuntimeException("No failed rows");

        String filename = "failures.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + filename);

        StatefulBeanToCsv<FailureTable> writer = new StatefulBeanToCsvBuilder<FailureTable>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        writer.write(failureTables);
    }
}
