package com.mastertbal.csvbackend.service.impl;

import com.mastertbal.csvbackend.exception.InvalidFileFormatException;
import com.mastertbal.csvbackend.model.dto.StudentDto;
import com.mastertbal.csvbackend.model.entity.Student;
import com.mastertbal.csvbackend.model.response.Failure;
import com.mastertbal.csvbackend.model.response.ImportSummary;
import com.mastertbal.csvbackend.repository.StudentRepository;
import com.mastertbal.csvbackend.service.StudentService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public ImportSummary uploadStudents(MultipartFile file) {
        if (file == null) throw new InvalidFileFormatException("File not available");
        int index = file.getOriginalFilename().indexOf('.');
        String extension = file.getOriginalFilename().substring(index + 1);
        if (!extension.equalsIgnoreCase("csv")) throw new InvalidFileFormatException("File format of " + extension + " not supported");

        return saveCsvStudents(file);
    }

    @Override
    public List<StudentDto> getImportedStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new RuntimeException("No student data in the database");
        }

        return students
                .stream()
                .map(this::toStudentDto)
                .toList();
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " missing"));
        return toStudentDto(student);
    }

    @Override
    public void exportStudentToCsv(HttpServletResponse response) throws Exception {
        List<StudentDto> importedStudents = getImportedStudents();

        String filename = "students.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + filename);

        StatefulBeanToCsv<StudentDto> writer = new StatefulBeanToCsvBuilder<StudentDto>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        writer.write(importedStudents);
    }

    private ImportSummary saveCsvStudents(MultipartFile file) {
        int imported = 0;
        int failed = 0;

        List<Failure> failures = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                    .withSkipLines(1)
                    .build();
            String[] lines;

            int csvRow = 1;

            while ( (lines = csvReader.readNext()) != null) {
                String studentFirstName = null;
                String studentLastName = null;
                String studentEmail = null;
                Integer studentAge = null;
                String studentCourse = null;

                for (int i = 0; i < lines.length; i++) {
                    if (i == 0) {
                        String fn = lines[i];
                        if (fn == null || fn.isBlank()) {
                            failures.add(new Failure(csvRow, "First name", "First name column is empty"));
                            studentFirstName = null;
                        } else {
                            studentFirstName = fn;
                        }
                    }

                    if (i == 1) {
                        String ln = lines[i];
                        if (ln == null || ln.isBlank()) {
                            failures.add(new Failure(csvRow, "Last name", "Last name column is empty"));
                            studentLastName = null;
                        } else {
                            studentLastName = ln;
                        }
                    }

                    if (i == 2) {
                        String em = lines[i];
                        if (em == null || em.isBlank()) {
                            failures.add(new Failure(csvRow, "Email","Email column is empty"));
                            studentEmail = null;
                        } else if (!em.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                            failures.add(new Failure(csvRow, "Email","Invalid email format"));
                            studentEmail = null;
                        } else {
                            studentEmail = em;
                        }
                    }

                    if (i == 3) {
                        Integer age = Integer.parseInt(lines[i]);
                        if (age == null) {
                            failures.add(new Failure(csvRow, "Age","Age column is empty"));
                            studentAge = null;
                        } else if (age <= 0) {
                            failures.add(new Failure(csvRow, "Age","Age is less than or equal to zero"));
                            studentAge = null;
                        } else {
                            studentAge = age;
                        }
                    }

                    if (i == 4) {
                        String course = lines[i];
                        if (course == null || course.isBlank()) {
                            failures.add(new Failure(csvRow,"Course", "Course column is empty"));
                            studentCourse = null;
                        } else {
                            studentCourse = course;
                        }
                    }
                }
                csvRow++;

                if (studentFirstName == null || studentLastName == null || studentEmail == null || studentAge == null || studentCourse == null) {
                    failed++;
                    continue;
                }

                imported++;
                Student student = Student.builder()
                        .id(null)
                        .firstName(studentFirstName)
                        .lastName(studentLastName)
                        .age(studentAge)
                        .email(studentEmail)
                        .course(studentCourse)
                        .build();
                System.out.println(student.toString());
                studentRepository.save(student);
            }

            ImportSummary summary = new ImportSummary(--csvRow, imported, failed, failures);
            System.out.println(summary.toString());
            return summary;
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private StudentDto toStudentDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .age(student.getAge())
                .course(student.getCourse())
                .build();
    }
}
