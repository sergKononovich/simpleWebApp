package com.mastery.java.task.dto;

import com.mastery.java.task.validator.ValidateDate;
import com.mastery.java.task.validator.ValidateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ValidationEmployee {
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

    @ValidateEnum(targetClassType = Gender.class, message = "Incorrect gender")
    private String gender;

    @NotNull(message = "birthday can't be null")
    @ValidateDate(message = "Invalid date. Correct date format: yyyy/MM/dd")
    private String birthday;
}
