package com.mastertbal.csvbackend.controller;

import com.mastertbal.csvbackend.service.FailureTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failures")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIS for Failure entities.",
        description = "A FailureController class for getting all failed entities information."
)
public class FailureController {

    private final FailureTableService failureTableService;

    @GetMapping
    @Operation(
            summary = "Get all failed entities from the database and export them to a csv file to be downloaded by the client"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    public void getAllFailures(
            @Parameter(description = "An object that represent the response to be sent back to the client.")
            HttpServletResponse response
    ) throws Exception {
        failureTableService.getAllFailures(response);
    }
}
