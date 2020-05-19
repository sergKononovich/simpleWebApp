package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;

import java.util.List;
import java.util.Optional;

/**
 * A DAO interface to handle the database operation
 * required to manipulate a Employee model.
 */
public interface EmployeeDao {

    /**
     * Persist new employee to database.
     * @param employee Employee.
     * @return Employee id.
     */
    Long create(Employee employee);

    /**
     * Get all employee from database.
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
