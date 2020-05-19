package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeDao employeeDao;

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(employeeDao);
    }

    @Test
    void shouldCreateEmployee() {
        Employee employee = createEmployee(1);

        when(employeeDao.create(employee)).thenReturn(1L);

        Long employeeId = employeeDao.create(employee);

        assertNotNull(employeeId);
        assertEquals(1L, employeeId);
    }

    @Test
    void shouldGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployee(1));
        employees.add(createEmployee(2));

        when(employeeDao.getAll()).thenReturn(employees);

        List<Employee> employeesFromDb = employeeService.getAll();

        assertNotNull(employeesFromDb);
        assertEquals(employees.get(0), employeesFromDb.get(0));
        assertEquals(employees.get(1), employeesFromDb.get(1));
    }

    @Test
    void shouldGetEmployeeById() {
        Employee testEmployee = createEmployee(1);

        when(employeeDao.getById(anyLong())).thenReturn(Optional.of(testEmployee));

        Optional<Employee> optionalEmployee = employeeService.getById(1L);

        assertTrue(optionalEmployee.isPresent());
        assertEquals(testEmployee, optionalEmployee.get());
    }

    @Test
    void shouldUpdateEmployee() {
        Employee testEmployee = createEmployee(1);

        when(employeeDao.update(testEmployee)).thenReturn(1);

        Integer numberOfUpdatedRecords = employeeService.update(testEmployee);

        assertNotNull(numberOfUpdatedRecords);
        assertEquals(1, numberOfUpdatedRecords);
    }

    @Test
    void shouldDeleteEmployeeById() {

        Long idOfDeletedEmployee = 1L;

        when(employeeDao.delete(idOfDeletedEmployee)).thenReturn(1);

        Integer numberOfDeletedRecords = employeeService.delete(idOfDeletedEmployee);

        assertNotNull(numberOfDeletedRecords);
        assertEquals(1, numberOfDeletedRecords);
    }

    private Employee createEmployee(long index) {
        Random random = new Random();

        Employee employee = new Employee();
        employee.setId(index);
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
