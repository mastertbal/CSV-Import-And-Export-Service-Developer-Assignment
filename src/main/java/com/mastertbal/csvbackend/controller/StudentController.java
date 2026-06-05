package com.mastertbal.csvbackend.controller;

import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.response.ImportSummary;
import com.mastertbal.csvbackend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIS for Student entities.",
        description = "A StudentController class for creating, importing, and getting student entities."
)
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/create-student")
    @Operation(
            summary = "Create a student entity",
            description = "It creates a new student entity and persist it in the database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status CREATED"
    )
    public ResponseEntity<StudentDto> createStudent(
            @Parameter(required = true, description = "An object that represent a student entity to be persisted")
            @Valid @RequestBody StudentDto studentDto
    ) {
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    @PostMapping("/import")
    @Operation(
            summary = "Create a student entities based on the number of rows in a csv file",
            description = "It uses a csv file to upload the student data and persist them into the database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status CREATED"
    )
    public ResponseEntity<ImportSummary> uploadStudents(
            @Parameter(required = true, description = "An object that represent the csv file to be imported")
            @RequestPart MultipartFile file
    ) {
        return new ResponseEntity<>(studentService.uploadStudents(file), HttpStatus.CREATED);
    }


    @GetMapping
    @Operation(
            summary = "Get all student entities persisted in the database",
            description = "It retrieves all student data in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    public ResponseEntity<List<StudentDto>> getImportedStudents() {
        return ResponseEntity.ok(studentService.getImportedStudents());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a student by the student id",
            description = "It gets a student using the student id in database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    public ResponseEntity<StudentDto> getStudentById(
            @Parameter(required = true, description = "ID of the student entity to be retrieved")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/export")
    @Operation(
            summary = "Get all student entities in the database and export them into a csv file format",
            description = "Gets all the students in the database, populate them into a students.csv file and export the file for download in the client"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    public void exportStudentToCsv(HttpServletResponse response) throws Exception {
        studentService.exportStudentToCsv(response);
    }
}
