package com.mastery.java.task.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Employee {
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull(message = "firstName can't be null")
    @NotEmpty(message = "firstName can't be empty")
    @NotBlank(message = "firstName can't be blank")
    private String firstName;

    @NotNull(message = "lastName can't be null")
    @NotEmpty(message = "lastName can't be empty")
    @NotBlank(message = "lastName can't be blank")
    private String lastName;

    @NotNull(message = "departmentId can't be null")
    @Min(value = 1, message = "departmentId must be more then 0")
    private Long departmentId;

    @NotNull(message = "jobTitle can't be null")
    @NotEmpty(message = "jobTitle can't be empty")
    @NotBlank(message = "jobTitle can't be blank")
    private String jobTitle;

    @NotNull(message = "gender can't be null")
    private Gender gender;

    @NotNull(message = "birthday can't be null")
    private LocalDate birthday;
}
