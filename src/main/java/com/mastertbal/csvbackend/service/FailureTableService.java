package com.mastertbal.csvbackend.service;

import com.mastertbal.csvbackend.model.entity.FailureTable;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface FailureTableService {

    FailureTable saveFailure(FailureTable failureTable);
    void getAllFailures(HttpServletResponse response) throws Exception;
}
