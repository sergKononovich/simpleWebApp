package com.mastery.java.task.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class Employee {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String firstName;
    private String lastName;
    private Long departmentId;
    private String jobTitle;
    private Gender gender;
    private LocalDate birthday;
}
