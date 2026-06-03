package com.mastertbal.csvbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private String course;
}
