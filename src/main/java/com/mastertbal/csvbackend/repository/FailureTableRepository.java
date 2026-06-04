package com.mastertbal.csvbackend.repository;

import com.mastertbal.csvbackend.model.entity.FailureTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailureTableRepository extends JpaRepository<FailureTable, Long> {
}
