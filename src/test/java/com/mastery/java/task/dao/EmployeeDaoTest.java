package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {

    @Mock
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    EmployeeDaoImpl employeeDao;

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void shouldCreateEmployee() {
        Employee employee = createEmployee(1);

        ReflectionTestUtils.setField(employeeDao, "sqlCreateEmployee", "create");

        when(namedParameterJdbcTemplate.update(anyString(),
                any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class)))
                .thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Map<String, Object> keyMap = new HashMap<>();
                keyMap.put("", 1);
                ((GeneratedKeyHolder)args[2]).getKeyList().add(keyMap);

                return 1;
            }
        }).thenReturn(1);

        assertEquals(1, employeeDao.create(employee));

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class));
    }

    @Test
    public void shouldGetAllEmployees() {

        ReflectionTestUtils.setField(employeeDao, "sqlGetAllEmployees", "get all");

        List<Employee> testEmployees = new ArrayList<>();
        testEmployees.add(createEmployee(0));
        testEmployees.add(createEmployee(1));

        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(testEmployees);

        List<Employee> employees = employeeDao.getAll();

        assertNotNull(employees);
        assertEquals(testEmployees.get(0), employees.get(0));
        assertEquals(testEmployees.get(1), employees.get(1));

        verify(namedParameterJdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void shouldGetEmployeeById() {

        Employee testEmployee = createEmployee(1);
        ReflectionTestUtils.setField(employeeDao, "sqlGetEmployeeById", "get by id");

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(testEmployee));

        Optional<Employee> optionalEmployee = employeeDao.getById(1L);
        assertTrue(optionalEmployee.isPresent());

        Employee employee = optionalEmployee.get();

        assertEquals(testEmployee, employee);
    }

    @Test
    public void shouldUpdateEmployee() {

        ReflectionTestUtils.setField(employeeDao, "sqlUpdateEmployee", "update employee");

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        Integer numberOfUpdatedRecords = employeeDao.update(createEmployee(1));

        assertNotNull(numberOfUpdatedRecords);
        assertEquals(1, numberOfUpdatedRecords);
    }

    @Test
    public void shouldDeleteEmployee() {

        ReflectionTestUtils.setField(employeeDao, "sqlDeleteEmployee", "delete employee");

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        Integer numberOfDeletedRecords = employeeDao.delete(1L);

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
