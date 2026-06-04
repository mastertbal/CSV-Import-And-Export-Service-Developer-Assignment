package com.mastertbal.csvbackend.service;

import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.response.ImportSummary;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    StudentDto createStudent(StudentDto studentDto);
    ImportSummary uploadStudents(MultipartFile file);
    List<StudentDto> getImportedStudents();
    StudentDto getStudentById(Long id);
    void exportStudentToCsv(HttpServletResponse response) throws Exception;
}
