package com.mastery.java.task.dto;

public class Employee {
    private Long employeeId;
    private String firstName;
    private Gender gender;

    public Long getEmployeeId() {
        return employeeId;
    }

    public Employee setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
