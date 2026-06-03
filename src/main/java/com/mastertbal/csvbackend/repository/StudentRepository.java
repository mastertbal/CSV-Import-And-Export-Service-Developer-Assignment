package com.mastertbal.csvbackend.repository;

import com.mastertbal.csvbackend.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
