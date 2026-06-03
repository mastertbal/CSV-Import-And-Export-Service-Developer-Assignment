package com.mastertbal.csvbackend.controller;

import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.response.ImportSummary;
import com.mastertbal.csvbackend.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/import")
    public ResponseEntity<ImportSummary> uploadStudents(@RequestPart MultipartFile file) {
        return ResponseEntity.ok(studentService.uploadStudents(file));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getImportedStudents() {
        return ResponseEntity.ok(studentService.getImportedStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/export")
    public void exportStudentToCsv(HttpServletResponse response) throws Exception {
        studentService.exportStudentToCsv(response);
    }
}
