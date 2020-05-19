package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Rollback
@Transactional
@SpringBootTest
public class EmployeeDaoIT {

    @Autowired
    EmployeeDaoImpl employeeDao;

    @Test
    public void shouldCreateEmployee() {

        Employee testEmployee = createEmployee(1);
        Long id = employeeDao.create(testEmployee);

        Optional<Employee> optionalEmployee = employeeDao.getById(id);
        assertTrue(optionalEmployee.isPresent());

        Employee employeeFromDb = optionalEmployee.get();

        assertNotNull(employeeFromDb);
        assertEquals(testEmployee, employeeFromDb);
    }

    @Test
    public void shouldGetAllEmployees() {
        List<Employee> testEmployees = new ArrayList<>();
        testEmployees.add(createEmployee(1));
        testEmployees.add(createEmployee(2));

        employeeDao.create(testEmployees.get(0));
        employeeDao.create(testEmployees.get(1));

        List<Employee> employeesFromDb = employeeDao.getAll();

        assertNotNull(employeesFromDb);
        assertEquals(testEmployees.get(0), employeesFromDb.get(0));
        assertEquals(testEmployees.get(1), employeesFromDb.get(1));
    }

    @Test
    public void shouldUpdateEmployee() {
        Employee testEmployee = createEmployee(1);
        Long id = employeeDao.create(testEmployee);

        testEmployee = createEmployee(2);
        testEmployee.setId(id);
        Integer numberOfUpdatedRecords = employeeDao.update(testEmployee);

        Optional<Employee> optionalEmployee = employeeDao.getById(id);
        assertTrue(optionalEmployee.isPresent());

        Employee employeeFromDb = optionalEmployee.get();

        assertEquals(testEmployee, employeeFromDb);
    }

    @Test
    public void shouldDeleteEmployee() {
        Employee testEmployee = createEmployee(1);
        Long id = employeeDao.create(testEmployee);

        Integer numberOfRecordsInDatabase = employeeDao.getAll().size();
        Integer numberOfDeletedRecords = employeeDao.delete(id);
        Integer numberOfRecordsAfterDelete = employeeDao.getAll().size();

        assertEquals(numberOfRecordsInDatabase, numberOfRecordsAfterDelete + 1);
        assertEquals(1, numberOfDeletedRecords);
    }

    private Employee createEmployee(long index) {
        Random random = new Random();

        Employee employee = new Employee();
        employee.setFirstName("First " + index );
        employee.setLastName("Last " + index);
        employee.setDepartmentId(index);
        employee.setGender(Gender.MALE);
        employee.setJobTitle("Title " + index);
        employee.setBirthday(LocalDate.of(random.nextInt(25) + 2000,
                Month.of(random.nextInt(12) + 1),
                random.nextInt(27) + 1));

        return employee;
    }
}
