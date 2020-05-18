package com.mastery.java.task.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Long departmentId;
    private String jobTitle;
    private Gender gender;
    private LocalDate birthday;
}
