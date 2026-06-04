package com.mastertbal.csvbackend.controller;

import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.response.ImportSummary;
import com.mastertbal.csvbackend.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
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
public class StudentController {

    private final StudentService studentService;

    @Tag(name = "Create a student", description = "It creates a new student entity and persist it in the database")
    @PostMapping("/create-student")
    public ResponseEntity<StudentDto> createStudent(
            @Parameter(required = true, description = "An object that represent a student entity to be persisted")
            @Valid @RequestBody StudentDto studentDto
    ) {
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    @Tag(name = "Upload students data", description = "It uses a csv file to upload the student data and persist them into the database")
    @PostMapping("/import")
    public ResponseEntity<ImportSummary> uploadStudents(
            @Parameter(required = true, description = "An object that represent the csv file to be imported")
            @RequestPart MultipartFile file
    ) {
        return new ResponseEntity<>(studentService.uploadStudents(file), HttpStatus.CREATED);
    }

    @Tag(name = "Get students data", description = "It retrieves all student data in the database")
    @GetMapping
    public ResponseEntity<List<StudentDto>> getImportedStudents() {
        return ResponseEntity.ok(studentService.getImportedStudents());
    }

    @Tag(name = "Get a student", description = "It gets a student using the student id in database")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(
            @Parameter(required = true, description = "ID of the student entity to be retrieved")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @Tag(name = "Export student data", description = "Gets all the students in the database, populate them into a students.csv file and export the file for download in the client")
    @GetMapping("/export")
    public void exportStudentToCsv(HttpServletResponse response) throws Exception {
        studentService.exportStudentToCsv(response);
    }
}
