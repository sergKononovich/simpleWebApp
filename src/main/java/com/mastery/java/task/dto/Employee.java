package com.mastery.java.task.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Employee {

    public Employee(ValidationEmployee validationEmployee){
        this.id = validationEmployee.getId();
        this.firstName = validationEmployee.getFirstName();
        this.lastName = validationEmployee.getLastName();
        this.departmentId = validationEmployee.getDepartmentId();
        this.birthday = LocalDate.parse(validationEmployee.getBirthday());
        this.gender = Gender.valueOf(validationEmployee.getGender());
    }

    @EqualsAndHashCode.Exclude
    private Long id;
    private String firstName;
    private String lastName;
    private Long departmentId;
    private String jobTitle;
    private Gender gender;
    private LocalDate birthday;
}
