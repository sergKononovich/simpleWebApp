package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for business logic.
 */
public interface EmployeeService {

    /**
     * Persist new employee.
     * @param employee Employee.
     * @return Employee id.
     */
    Long create(Employee employee);

    /**
     * Get all employee.
     * @return Employee list.
     */
    List<Employee> getAll();

    /**
     * Get employee by id.
     * @param id employee id.
     * @return Employee.
     */
    Optional<Employee> getById(Long id);

    /**
     * Update employee.
     * @param employee Employee.
     * @return number of updated records.
     */
    Integer update(Employee employee);

    /**
     * Delete employee by id.
     * @param id id.
     * @return number of updated records.
     */
    Integer delete(Long id);
}
